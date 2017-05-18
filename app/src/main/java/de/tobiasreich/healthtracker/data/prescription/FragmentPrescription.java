package de.tobiasreich.healthtracker.data.prescription;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import de.tobiasreich.healthtracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPrescription extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomPagerAdapter adapter;

    private int selectedTabPosition;

    private List<MedicineDataObject> objectList;

    public FragmentPrescription() {
        objectList = new ArrayList<>();
        for (int i = 0; i<10;i++) {
            objectList.add(new MedicineDataObject("Medicine " + i, i));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_prescription, container, false);
        tabLayout = (TabLayout) rootView.findViewById(R.id.my_tab_layout);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        adapter = new CustomPagerAdapter(getActivity(), objectList);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                viewPager.setCurrentItem(tab.getPosition());
                selectedTabPosition = viewPager.getCurrentItem();
                Log.d("Selected", "Selected " + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
                Log.d("Unselected", "Unselected " + tab.getPosition());
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

        return rootView;
    }

}
