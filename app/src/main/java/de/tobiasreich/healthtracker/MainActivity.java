package de.tobiasreich.healthtracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new FragmentA());
            }
        });

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new FragmentB());
            }
        });
    }

    private void switchToFragment(final Fragment fragment) {
        fMan.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment.getClass().getSimpleName())
            .commit();
        fMan.executePendingTransactions();
    }

}
