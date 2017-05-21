package de.tobiasreich.healthtracker.data.medicineList;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.data.myMedicine.Medicine;

/**
 * Created by T on 01.05.2017. */
class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<Medicine> meds;
    private Context context;

    public MedicineAdapter(List<Medicine> meds, Context context) {
        this.meds = meds;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medicine_list_view_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medicine medicine = meds.get(position);

        if (position % 2 == 0)
            holder.rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrayOdd));
        else
            holder.rootView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
        holder.titleTV.setText(medicine.getMedicineName());
        holder.descriptionTV.setText(medicine.description);
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView titleTV;
        public TextView descriptionTV;
        public ImageView imageView;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            titleTV = (TextView) rootView.findViewById(R.id.titleTV);
            descriptionTV = (TextView) rootView.findViewById(R.id.descriptionTV);
            imageView = (ImageView) rootView.findViewById(R.id.imageView);
        }
    }

}
