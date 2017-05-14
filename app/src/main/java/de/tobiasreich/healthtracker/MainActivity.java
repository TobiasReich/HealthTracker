package de.tobiasreich.healthtracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Calendar;

import de.tobiasreich.healthtracker.data.medicineList.FragmentMedicineList;
import de.tobiasreich.healthtracker.data.profile.settings.FragmentSettings;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String DETAIL_TEXT = "DETAIL_TEXT";

    private final FragmentManager fMan = getSupportFragmentManager();

    private LinearLayout fragmentContainer;
    private Button buttonA;
    private Button buttonB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MAIN");
        setContentView(R.layout.activity_main);

        fragmentContainer = (LinearLayout) findViewById(R.id.fragmentContainer);
        buttonA = (Button) findViewById(R.id.buttonA);
        buttonB = (Button) findViewById(R.id.buttonB);
        buttonA.setOnClickListener(v -> switchToFragment(new FragmentMedicineList()));

        buttonB.setOnClickListener(v -> switchToFragment(new FragmentB()));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            switchToFragment(new FragmentSettings());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void switchToFragment(final Fragment fragment) {
        fMan.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment.getClass().getSimpleName())
            .commit();
        fMan.executePendingTransactions();
    }


    @SuppressLint("WrongConstant")
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, ">>> Time in Millis: " + System.currentTimeMillis());

        Calendar cal = Calendar.getInstance();
        Log.i(TAG, ">>> Calendar-Time in Millis: " + cal.getTimeInMillis());

        Log.i(TAG, ">>> Calendar-Time Year: " + cal.get(Calendar.YEAR));
        Log.i(TAG, ">>> Calendar-Time Month: " + cal.get(Calendar.MONTH));
        Log.i(TAG, ">>> Calendar-Time DOM: " + cal.get(Calendar.DAY_OF_MONTH));
        Log.i(TAG, ">>> Calendar-Time DOW: " + cal.get(Calendar.DAY_OF_WEEK));
        Log.i(TAG, ">>> Calendar-Time DOWIM: " + cal.get(Calendar.DAY_OF_WEEK_IN_MONTH));


    }
}
