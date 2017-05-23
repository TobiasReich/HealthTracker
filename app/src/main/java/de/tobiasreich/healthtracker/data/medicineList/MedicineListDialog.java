package de.tobiasreich.healthtracker.data.medicineList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

import de.tobiasreich.healthtracker.R;
import de.tobiasreich.healthtracker.data.database.DataBaseHelper;
import de.tobiasreich.healthtracker.data.database.DataManager;
import de.tobiasreich.healthtracker.data.myMedicine.Medicine;

/**
 * Dialog for creating new Vehicles
 * <p>
 * Created by tr on 17/03/17.
 */
public class MedicineListDialog extends Dialog {

    private final String TAG = getClass().getSimpleName();

    private Context context;
    private Bitmap medicinePhoto;

    private ImageView medicineIV;

    public MedicineListDialog(final DataBaseHelper db, IMedicineListUpdate callback,
                              FragmentMedicineList fragment, final Context context) {
        super(context);
        this.context = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_medicine_list);

        setCancelable(true);

        Window window = getWindow();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        medicineIV = (ImageView) findViewById(R.id.medicineIV);
        ImageButton addPhotoButton = (ImageButton) findViewById(R.id.addPhotoButton);
        Button saveMedicineButton = (Button) findViewById(R.id.saveMedicineButton);
        EditText descriptionET = (EditText) findViewById(R.id.descriptionET);
        EditText amountET = (EditText) findViewById(R.id.amountET);
        AutoCompleteTextView medicineACTV = (AutoCompleteTextView) findViewById(R.id.medicineACTV);

        List<String> allMedicins = DataManager.getMedicinesAutoCompleteList(context);

        findViewById(R.id.cancelButton).setOnClickListener(v -> dismiss());

        medicineACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable text) {
                if (text.toString().trim().length() >= 3)
                    saveMedicineButton.setEnabled(true);
                else
                    saveMedicineButton.setEnabled(false);
            }
        });

        addPhotoButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                fragment.startActivityForResult(takePictureIntent, FragmentMedicineList.REQUEST_MEDICINE_IMAGE_CAPTURE);
            }
        });


        ArrayAdapter<String> autoCompleteAdapter
                = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, allMedicins);
        medicineACTV.setAdapter(autoCompleteAdapter);

        saveMedicineButton.setOnClickListener(v -> {
            Medicine newMed = new Medicine();
            newMed.name = medicineACTV.getText().toString();
            newMed.description = descriptionET.getText().toString();
            double amount = 0;
            try {
                amount = Double.parseDouble(amountET.getText().toString());
            } catch (Exception ex) {
                Log.e(TAG, "Error parsing the amount " + amountET.getText().toString() + ". Set it to 0");
            }
            newMed.amount = (int) amount;
            db.insertMedicine(newMed);
            Log.i(TAG, "Adding medicine done");
            callback.updateListOfMedicines();
            dismiss();
        });
    }

    public void setBitmap(Bitmap bitmap){
        this.medicinePhoto = bitmap;
        Glide.with(context)
                .load(DataManager.bitmapToByte(bitmap))
                .asBitmap().override(300, 300)
                .fitCenter()
                .into(medicineIV);
    }
}
