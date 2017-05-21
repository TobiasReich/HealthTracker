package de.tobiasreich.healthtracker.data.medicineList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.data.database.DataBaseHelper;
import de.tobiasreich.healthtracker.data.database.DataManager;
import de.tobiasreich.healthtracker.data.myMedicine.Medicine;


public class FragmentMedicineList extends Fragment {

    private static final String TAG = FragmentMedicineList.class.getSimpleName();

    private AutoCompleteTextView medicineACTV;

    // Database Helper
    private DataBaseHelper db;

    private Button addButton;


    private RecyclerView medicineRecycleView;
    private LinearLayoutManager medicineRVLayoutManager;
    private MedicineAdapter medicineAdapter;

    private List<String> allMedicins;



    public FragmentMedicineList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate -> creating new db");
        db = new DataBaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_a, container, false);

        addButton = (Button) rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            String name = medicineACTV.getText().toString();

            if (allMedicins.contains(name)) {
                Medicine newMed = new Medicine(name);
                newMed.description = "Esto es una descripcion interesante";
                db.insertMedicine(newMed);
                Log.i(TAG, "Adding medicine done");
                //medicineAdapter.notifyDataSetChanged();
                updateListOfMedicines();
            } else {
                Toast.makeText(getActivity(),
                    "Error, please select a medicine from the list", Toast.LENGTH_LONG).show();
            }
        });

        allMedicins = DataManager.getMedicines(getResources().openRawResource(R.raw.medicines));
        medicineACTV = (AutoCompleteTextView) rootView.findViewById(R.id.medicineACTV);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, allMedicins);
        medicineACTV.setAdapter(adapter);

        // RECYCLER VIEW
        medicineRecycleView = (RecyclerView) rootView.findViewById(R.id.medicineRecycleView);
        medicineRecycleView.setHasFixedSize(true);

        medicineRVLayoutManager = new LinearLayoutManager(getActivity());
        medicineRecycleView.setLayoutManager(medicineRVLayoutManager);

        updateListOfMedicines();

        return rootView;
    }

    private void updateListOfMedicines(){
        List<Medicine> userMeds = db.getAllMedicines();
        medicineAdapter = new MedicineAdapter(userMeds, getActivity());
        medicineRecycleView.setAdapter(medicineAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Log.d(TAG, "Closing DB");
        db.closeDB();
    }
}
