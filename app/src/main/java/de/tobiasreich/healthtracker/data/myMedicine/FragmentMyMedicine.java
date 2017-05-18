package de.tobiasreich.healthtracker.data.myMedicine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.data.database.DataBaseHelper;


public class FragmentMyMedicine extends Fragment {


    private EditText durationET;
    private EditText qualityET;
    private EditText noteET;
    private Button insertButton;

    private DataBaseHelper dbHelper;

    public FragmentMyMedicine() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DataBaseHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_medicine, container, false);

        durationET = (EditText) rootView.findViewById(R.id.durationET);
        qualityET = (EditText) rootView.findViewById(R.id.qualityET);
        noteET = (EditText) rootView.findViewById(R.id.noteET);
        insertButton = (Button) rootView.findViewById(R.id.insertButton);
        insertButton.setOnClickListener(v -> {
            long result = dbHelper.insertData(5.5, System.currentTimeMillis(), 5, "Slept well!");
            Toast.makeText(getActivity(), "Result: " + result, Toast.LENGTH_LONG).show();
        });
        return rootView;
    }

 }
