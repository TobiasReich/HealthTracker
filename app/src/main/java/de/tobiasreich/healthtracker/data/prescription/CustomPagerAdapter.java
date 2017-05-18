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
    private List<DataObject> objectList;

    public CustomPagerAdapter(Context context, List<DataObject> medicineList) {
        this.context = context;
        this.objectList = medicineList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.view_data, collection, false);
        TextView valueTV = (TextView) layout.findViewById(R.id.valueTV);

        DataObject dataObject = objectList.get(position);
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