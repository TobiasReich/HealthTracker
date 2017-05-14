package de.tobiasreich.healthtracker.data.profile.settings;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.ui.DateTimePickerDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSettings extends Fragment implements DateTimePickerDialog.DateTimeListener {

    private static final String TAG = FragmentSettings.class.getSimpleName();


    public static final SimpleDateFormat FORMAT_SIMPLE_DATE = new SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault());    // 15.10.2016
    public static final SimpleDateFormat FORMAT_SIMPLE_DATE_WITH_TIME = new SimpleDateFormat("dd.MM.yyyy - HH:mm", java.util.Locale.getDefault()); // 15.10.2016 - 10:30
    public static final SimpleDateFormat FORMAT_FULL_DATE_WITH_DAY = new SimpleDateFormat("EE, d. MMMM yyyy", java.util.Locale.getDefault());   // Mo., 15. October 2016
    public static final SimpleDateFormat FORMAT_FULL_DATE_WITH_DAY_AND_TIME = new SimpleDateFormat("EEEE d. MMMM yyyy - HH:mm", java.util.Locale.getDefault());   // Mo., 15. 10. 2017 - 10:30


    private static final int USER_SEX_UNKNOWN = 0;
    private static final int USER_SEX_MALE = 1;
    private static final int USER_SEX_FEMALE = 2;

    SharedPreferences sharedPref;

    private long birthdayTimeStamp;

    private EditText nameET;
    private EditText birthdayET;
    private EditText weightET;
    private EditText heightET;
    private RadioButton maleRB;
    private RadioButton femaleRB;


    public FragmentSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //sharedPref = getActivity().getSharedPreferences("tobi", Context.MODE_PRIVATE);

        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        nameET = (EditText) rootView.findViewById(R.id.nameET);
        birthdayET = (EditText) rootView.findViewById(R.id.birthdayET);
        birthdayET.setOnClickListener(v -> new DateTimePickerDialog(getActivity(), true, birthdayTimeStamp, FragmentSettings.this).show());
        weightET = (EditText) rootView.findViewById(R.id.weightET);
        heightET = (EditText) rootView.findViewById(R.id.heightET);

        maleRB = (RadioButton) rootView.findViewById(R.id.maleRB);
        femaleRB = (RadioButton) rootView.findViewById(R.id.femaleRB);

        rootView.findViewById(R.id.saveButton).setOnClickListener(v -> {
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preferences_user_name_key), nameET.getText().toString());
                    editor.putLong(getString(R.string.preferences_user_birthday_key), birthdayTimeStamp);
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

        String name = sharedPref.getString(getString(R.string.preferences_user_name_key), "");
        nameET.setText(name);

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



        Calendar birthdayCal = Calendar.getInstance();
        birthdayCal.setTimeInMillis(sharedPref.getLong(getString(R.string.preferences_user_birthday_key), 0));
        onDateTimeSelected(birthdayCal);
    }

    @Override
    public void onDateTimeSelected(Calendar calendar) {
        birthdayTimeStamp = calendar.getTimeInMillis();
        Log.d(TAG, "Changed the birthday: " + birthdayTimeStamp);
        birthdayET.setText(FORMAT_FULL_DATE_WITH_DAY_AND_TIME .format(calendar.getTime()));
    }

}
