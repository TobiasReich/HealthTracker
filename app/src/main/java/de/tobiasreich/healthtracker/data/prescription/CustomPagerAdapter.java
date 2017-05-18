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
    private List<MedicineDataObject> objectList;

    public CustomPagerAdapter(Context context, List<MedicineDataObject> medicineList) {
        this.context = context;
        this.objectList = medicineList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.prescription_item_view, collection, false);
        TextView valueTV = (TextView) layout.findViewById(R.id.valueTV);
        TextView nameTV = (TextView) layout.findViewById(R.id.nameTV);

        MedicineDataObject dataObject = objectList.get(position);

        nameTV.setText(dataObject.name);

        valueTV.setText("Value: " + dataObject.value);

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