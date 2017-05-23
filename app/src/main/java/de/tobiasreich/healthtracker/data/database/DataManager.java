package de.tobiasreich.healthtracker.data.database;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.tobiasreich.healthtracker.R;

/**
 * Created by T on 01.05.2017.
 */
public class DataManager {

    private static final String TAG = DataManager.class.getSimpleName();
    private Context context;

    private static DataManager Instance;

    public static final int IMAGE_WIDTH = 1024;
    public static final int IMAGE_HEIGHT = 1024;
    public static final int JPG_FILE_COMPRESSION_VALUE = 85;

    public static synchronized DataManager instance() {
        if (Instance == null) {
            Instance = new DataManager();
        }
        return Instance;
    }

    public void registerContext(Context context) {
        this.context = context;
    }

    public static List<String> getMedicinesAutoCompleteList(Context context) {
        List<String> resultList = new ArrayList<>();
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.medicines)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine;
            while ((readLine = reader.readLine()) != null)
                resultList.add(readLine);
        } catch (IOException ex) {
            throw new RuntimeException("Error in reading CSV file: " + ex);
        }
        return resultList;
    }

    /**
     * Closes the keyboard.
     * <p>
     * This is a static method used in order to close the keyboard manually all the multiple times this is needed.
     * <p>
     * Shame on you google for messing this up!
     *
     * @param activity Activity for getting the focus
     */
    public static void closeSoftwareKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
          /*  imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * Shows a keyboard if the keyboard is not showing up on itself
     *
     * @param activity Activity
     */
    public static void showSoftwareKeyboardImplicit(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    /** Transfers a Bitmap to a byte array so it can be shown via glide
     *
     * @param bitmap Bitmap to transfer
     * @return byte[] with the image to show
     */
    public static byte[] bitmapToByte(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    /** Creates a new image-file which will be written, once the camera has
     * succeeded writing the picture (Or deleted if user aborts the task).
     *
     * Note: This has to check the "write to SD"-permission.
     *
     * @return File that was created*/
    public static File createTempImageFileForCamera(String medicineName, int ingredientAmount, Context context) throws IOException {
        //File storageDir = getMedicineImagePath(context);
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d(TAG, "Required Path: " + storageDir.getAbsolutePath());
        File tempFile = new File(storageDir,medicineName + "_" + ingredientAmount + ".jpg");
        Log.d(TAG, "Prepared Camera Image: " + tempFile.getAbsolutePath());
        return tempFile;
    }

    /** Returns Generates the path String for a certain
     *
     * @return Path as String */
    private static File getMedicineImagePath(Context context){
        return context.getDir("medicine_images", Context.MODE_PRIVATE);
    }

    /** This ensures the creation of a given path. If this path did not exist, the method tries to
     * create it. The return value determins the result.
     *
     * @param path String path to create
     * @return boolean true if path exists in the end. */
    private static boolean ensurePathExists(String path){
        // Get the directory for the user's public pictures directory.
        File root = new File(path);
        if ( ! root.exists() ){
            Log.d(TAG, "Directory " + root.toString() + " did not exist.");
            if (!root.mkdirs()) {
                Log.e(TAG, "ERROR: Directory " + root.toString() + " was not created mkdirs");
                return false;
            }else
                Log.i(TAG, "Directory " + root.toString() + " was created! All fine so far!");
        }
        return true;
    }



}

