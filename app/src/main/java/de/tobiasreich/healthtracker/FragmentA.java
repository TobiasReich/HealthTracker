package de.tobiasreich.healthtracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FragmentA extends Fragment {

    private AutoCompleteTextView medicineACTV;

    private Button addButton;


    private RecyclerView medicineRecycleView;
    private LinearLayoutManager medicineRVLayoutManager;
    private MedicineAdapter medicineAdapter;

    private List<String> allMedicins;
    private List<Medicine> userMeds = new ArrayList<>();


    public FragmentA() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_a, container, false);

        addButton = (Button) rootView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = medicineACTV.getText().toString();
                if (allMedicins.contains(name)) {
                    Medicine newMed = new Medicine();
                    newMed.title = name;
                    newMed.description = "Esto es una descripcion interesante";
                    userMeds.add(newMed);
                    medicineAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(),
                        "Error, please select a medicine from the list", Toast.LENGTH_LONG).show();
                }
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

        medicineAdapter = new MedicineAdapter(userMeds, getActivity());

        medicineRecycleView.setAdapter(medicineAdapter);

        return rootView;
    }


}
