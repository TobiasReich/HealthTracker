package de.tobiasreich.healthtracker;


import android.content.res.Resources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FragmentA extends Fragment {

    private RecyclerView medicineRecycleView;
    private LinearLayoutManager medicineRVLayoutManager;
    private MedicineAdapter medicineAdapter;


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

        medicineRecycleView = (RecyclerView) rootView.findViewById(R.id.medicineRecycleView);
        medicineRecycleView.setHasFixedSize(true);

        medicineRVLayoutManager = new LinearLayoutManager(getActivity());
        medicineRecycleView.setLayoutManager(medicineRVLayoutManager);


        List<Medicine> meds = Medicine.getRandomMedicine(); // DataBase.getMedicine()
        medicineAdapter = new MedicineAdapter(meds, getActivity());

        medicineRecycleView.setAdapter(medicineAdapter);

        return rootView;
    }


}
