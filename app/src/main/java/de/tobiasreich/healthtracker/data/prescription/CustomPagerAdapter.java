package de.tobiasreich.healthtracker.data.prescription;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.tobiasreich.healthtracker.R;

public class CustomPagerAdapter extends PagerAdapter {

    private Context context;
    private List<MedicinePrescriptionObject> objectList;

    public CustomPagerAdapter(Context context, List<MedicinePrescriptionObject> medicineList) {
        this.context = context;
        this.objectList = medicineList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.prescription_item_view, collection, false);

        MedicinePrescriptionObject dataObject = objectList.get(position);

        ((TextView) layout.findViewById(R.id.nameTV)).setText(dataObject.name);
        ((TextView) layout.findViewById(R.id.nextUsageTV)).setText("10.10.2017 - 10:30");
        ((TextView) layout.findViewById(R.id.infoTV)).setText("Drink water!");
        ((TextView) layout.findViewById(R.id.amountTV)).setText(Integer.toString(dataObject.amount));
        ((TextView) layout.findViewById(R.id.intervallTV)).setText("Every day!");

        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return objectList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return objectList.get(position).name;
    }

}