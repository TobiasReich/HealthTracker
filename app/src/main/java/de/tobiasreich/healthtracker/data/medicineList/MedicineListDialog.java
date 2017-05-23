package de.tobiasreich.healthtracker.data.medicineList;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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

import java.io.File;
import java.io.IOException;
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

    private static final String FILE_PROVIDER_AUTHORITY = "de.tobiasreich.healthtracker.fileprovider";

    private Context context;
    private File photoFile;

    private AutoCompleteTextView medicineACTV;
    private EditText descriptionET;
    private EditText amountET;
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
        descriptionET = (EditText) findViewById(R.id.descriptionET);
        amountET = (EditText) findViewById(R.id.amountET);
        medicineACTV = (AutoCompleteTextView) findViewById(R.id.medicineACTV);

        List<String> allMedicins = DataManager.getMedicinesAutoCompleteList(context);

        findViewById(R.id.cancelButton).setOnClickListener(v -> dismiss());

        medicineACTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable text) {
                if (text.toString().trim().length() >= 3)
                    saveMedicineButton.setEnabled(true);
                else
                    saveMedicineButton.setEnabled(false);
            }
        });

        addPhotoButton.setOnClickListener(v -> {
            Intent getCameraImageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            photoFile = null;
            try {
                photoFile = DataManager.createTempImageFileForCamera(getName(), getAmountValue(), context);
            } catch (IOException e) {
                Log.e(TAG, "Error creating photo-file");
                return;
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(context,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);
                getCameraImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                fragment.startActivityForResult(getCameraImageIntent, FragmentMedicineList.REQUEST_MEDICINE_IMAGE_CAPTURE);
            }
        });

        ArrayAdapter<String> autoCompleteAdapter
                = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, allMedicins);
        medicineACTV.setAdapter(autoCompleteAdapter);

        saveMedicineButton.setOnClickListener(v -> {
            Medicine newMed = new Medicine();

            newMed.name = getName();
            newMed.description = getDescription();
            newMed.amount = getAmountValue();
            newMed.imagePath = getImageName();

            db.insertMedicine(newMed);
            callback.updateListOfMedicines();
            dismiss();
        });
    }

    private String getDescription() {
        return descriptionET.getText().toString();
    }

    /**
     * Gets the value from the amount EditText.
     * <p>
     * Returns 0 if no valid value is given.
     *
     * @return int with the amount; 0 if anything goes wrong
     */
    private int getAmountValue() {
        double amount = 0;
        try {
            amount = Double.parseDouble(amountET.getText().toString());
        } catch (Exception ex) {
            Log.e(TAG, "Error parsing the amount " + amountET.getText().toString() + ". Set it to 0");
        }
        return (int) amount;
    }

    /**
     * Gets the imagePath in case there is one.
     * <p>
     * Returns an empty String in case no imageName exists.
     *
     * @return String with the image Name (without a path)
     */
    public String getImageName() {
        if (photoFile != null)
            return photoFile.getName();
        return "";
    }

    /**
     * Gets the name written in the AutoCompleteTextView
     *
     * @return String with the name
     */
    public String getName() {
        return medicineACTV.getText().toString();
    }


    /**
     * Callback Method for showing the recently made camera image in the ImageView
     */
    public void showBitmap() {
        Glide.with(context)
                .load(photoFile)
                .asBitmap().override(300, 300)
                .fitCenter()
                .into(medicineIV);
    }


}
