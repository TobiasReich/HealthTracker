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
import android.widget.RadioButton;
import android.widget.Toast;

import de.tobiasreich.healthtracker.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends Fragment {

    private static final int USER_SEX_UNKNOWN = 0;
    private static final int USER_SEX_MALE = 1;
    private static final int USER_SEX_FEMALE = 2;

    SharedPreferences sharedPref;

    String name;
    String birthday;
    String sex;
    String weight;
    String height;

    EditText nameET;
    EditText birthdayET;
    EditText weightET;
    EditText heightET;
    RadioButton maleRB;
    RadioButton femaleRB;


    public FragmentSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        nameET = (EditText) rootView.findViewById(R.id.nameET);
        birthdayET = (EditText) rootView.findViewById(R.id.birthdayET);
        weightET = (EditText) rootView.findViewById(R.id.weightET);
        heightET = (EditText) rootView.findViewById(R.id.heightET);

        maleRB = (RadioButton) rootView.findViewById(R.id.maleRB);
        femaleRB = (RadioButton) rootView.findViewById(R.id.femaleRB);

        rootView.findViewById(R.id.saveButton).setOnClickListener(v -> {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preferences_user_name_key), nameET.getText().toString());
                    editor.putString(getString(R.string.preferences_user_birthday_key), birthdayET.getText().toString());
                    editor.putFloat(getString(R.string.preferences_user_weight_key), Float.parseFloat(weightET.getText().toString()));
                    editor.putFloat(getString(R.string.preferences_user_height_key), Float.parseFloat(heightET.getText().toString()));
                    if (maleRB.isChecked())
                        editor.putInt(getString(R.string.preferences_user_sex_key), USER_SEX_MALE);
                    else if (femaleRB.isChecked())
                        editor.putInt(getString(R.string.preferences_user_sex_key), USER_SEX_FEMALE);
                    editor.apply();
                    Toast.makeText(getActivity(), "Data saved!", Toast.LENGTH_LONG).show();
                }
        );
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String name = sharedPref.getString(getString(R.string.preferences_user_name_key), "XYZ");
        nameET.setText(name);

        String birthday = sharedPref.getString(getString(R.string.preferences_user_birthday_key), "01.01.1900");
        birthdayET.setText(birthday);

        float weight = sharedPref.getFloat(getString(R.string.preferences_user_weight_key), 0);
        weightET.setText(Float.toString(weight));

        float height = sharedPref.getFloat(getString(R.string.preferences_user_height_key), 0);
        heightET.setText(Float.toString(height));

        int sex = sharedPref.getInt(getString(R.string.preferences_user_sex_key), USER_SEX_UNKNOWN);
        if (sex == USER_SEX_MALE){
            maleRB.setChecked(true);
        } else if (sex == USER_SEX_FEMALE){
            femaleRB.setChecked(true);
        }

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