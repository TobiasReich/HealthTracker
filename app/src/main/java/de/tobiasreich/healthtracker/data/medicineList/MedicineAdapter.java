package de.tobiasreich.healthtracker.data.medicineList;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;
import java.util.Locale;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.data.database.DataBaseHelper;
import de.tobiasreich.healthtracker.data.database.DataManager;
import de.tobiasreich.healthtracker.data.myMedicine.Medicine;

/**
 * Created by T on 01.05.2017. */
class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private static final String TAG = MedicineAdapter.class.getSimpleName();

    private List<Medicine> meds;
    private Context context;
    private IMedicineListUpdate updateCallback;
    private DataBaseHelper dbHelper;

    public MedicineAdapter(List<Medicine> meds, Context context,
                           IMedicineListUpdate updateCallback, DataBaseHelper dbHelper) {
        this.meds = meds;
        this.context = context;
        this.updateCallback = updateCallback;
        this.dbHelper = dbHelper;
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

        // Name
        holder.titleTV.setText(medicine.getMedicineName() + " (id: " + medicine.id + ")");

        // Description optional
        if (medicine.description == null || "".equals(medicine.description)) {
            holder.descriptionTV.setVisibility(View.GONE);
        } else {
            holder.descriptionTV.setVisibility(View.VISIBLE);
            holder.descriptionTV.setText(medicine.description);
        }

        // amount
        if (medicine.amount == 0) {
            holder.amountTV.setVisibility(View.GONE);
        } else {
            holder.amountTV.setVisibility(View.VISIBLE);
            holder.amountTV.setText(String.format(Locale.getDefault(),"%d", medicine.amount));
        }

        // Image
        if (medicine.imagePath == null || "".equals(medicine.imagePath)) {
            Glide.with(context)
                    .load("")
                    .placeholder(R.drawable.medicine_placeholder)
                    .override(300, 300)
                    .fitCenter()
                    .into(holder.medicineIV);
        } else {
            //Log.d(TAG, "Image File for " + medicine.name + " : " + medicine.imagePath);
            File imageFile = new File(DataManager.getMedicineImagePath(context), medicine.imagePath);
            Glide.with(context)
                    .load(imageFile)
                    .asBitmap()
                    .override(300, 300)
                    .fitCenter()
                    .into(holder.medicineIV);
        }

        // deleting
        holder.deleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.dialog_title_are_you_sure)
                    .setMessage(String.format(context.getString(R.string.dialog_delete_medicine_from_list_message), medicine.name, medicine.amount))
                    .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                        dbHelper.deleteMedicineFromList(medicine.id);
                        updateCallback.updateListOfMedicines();
                    })
                    .setNegativeButton(android.R.string.cancel, (dialog, id) -> {
                        dialog.dismiss();
                    });
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final private View rootView;
        final TextView titleTV;
        final TextView descriptionTV;
        final TextView amountTV;
        final ImageView medicineIV;
        final ImageButton deleteButton;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            titleTV = (TextView) rootView.findViewById(R.id.titleTV);
            descriptionTV = (TextView) rootView.findViewById(R.id.descriptionTV);
            amountTV = (TextView) rootView.findViewById(R.id.amountTV);
            medicineIV = (ImageView) rootView.findViewById(R.id.medicineIV);
            deleteButton = (ImageButton) rootView.findViewById(R.id.deleteButton);
        }
    }

}
