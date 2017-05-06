package de.tobiasreich.healthtracker.data.profile.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.tobiasreich.healthtracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends Fragment {

    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

    String name;
    String birthday;
    String sex;
    String weight;
    String height;

    EditText nameET;

    public FragmentSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        nameET = (EditText) rootView.findViewById(R.id.nameET);

        rootView.findViewById(R.id.saveButton).setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Saving", Toast.LENGTH_LONG).show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.preferences_user_name_key), nameET.getText().toString());
            editor.apply();
            }
        );
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String name = sharedPref.getString(getString(R.string.preferences_user_name_key), "XYZ");
        nameET.setText(name);
    }

}



/*
* <Prefrences name="de.tobaisreich.healthtracker">
*      <edat>100</edat>
* </Preferences>
*
* <Prefrences name="tobi">
*      <edat>100</edat>
* </Preferences>
*
* <Prefrences name="tobias">
*      <edat>100</edat>
* </Preferences>
*
* <Prefrences name="dayane">
*      <edat>99</edat>
* </Preferences>
*
*
*
*
*
*
*
*
*
*
*
*
* */