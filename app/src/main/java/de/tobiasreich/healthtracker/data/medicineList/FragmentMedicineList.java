package de.tobiasreich.healthtracker.data.medicineList;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.data.database.DataBaseHelper;
import de.tobiasreich.healthtracker.data.myMedicine.Medicine;


public class FragmentMedicineList extends Fragment implements IMedicineListUpdate{

    private static final String TAG = FragmentMedicineList.class.getSimpleName();

    private AutoCompleteTextView medicineACTV;

    // Database Helper
    private DataBaseHelper db;

    private Button addButton;

    private boolean sortByName = true;

    private Spinner orderSpinner;
    private ImageButton clearButton;
    private RecyclerView medicineRecycleView;
    private LinearLayoutManager medicineRVLayoutManager;
    private TextView noMedicineInListTV;
    private MedicineAdapter medicineAdapter;


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

        View rootView = inflater.inflate(R.layout.fragment_medicine_list, container, false);

        addButton = (Button) rootView.findViewById(R.id.addButton);
        medicineACTV = (AutoCompleteTextView) rootView.findViewById(R.id.medicineACTV);
        medicineRecycleView = (RecyclerView) rootView.findViewById(R.id.medicineRecycleView);
        orderSpinner = (Spinner) rootView.findViewById(R.id.orderSpinner);
        noMedicineInListTV = (TextView) rootView.findViewById(R.id.noMedicineInListTV);

        addButton.setOnClickListener(v -> new MedicineListDialog(db, this, getActivity()).show());

        medicineRecycleView.setHasFixedSize(true);
        medicineRVLayoutManager = new LinearLayoutManager(getActivity());
        medicineRecycleView.setLayoutManager(medicineRVLayoutManager);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sortOrder,  R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortByName = (position == 0);
                updateListOfMedicines();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortByName = true;
                updateListOfMedicines();
            }
        });
        orderSpinner.setAdapter(adapter);

        updateListOfMedicines();

        return rootView;
    }

    public void updateListOfMedicines() {
        List<Medicine> userMeds = db.getAllMedicines(sortByName);
        if (userMeds.size() == 0) {
            noMedicineInListTV.setVisibility(View.VISIBLE);
            medicineRecycleView.setVisibility(View.GONE);
        } else {
            noMedicineInListTV.setVisibility(View.GONE);
            medicineRecycleView.setVisibility(View.VISIBLE);
            medicineAdapter = new MedicineAdapter(userMeds, getActivity());
            medicineRecycleView.setAdapter(medicineAdapter);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        db.closeDB();
    }
}
