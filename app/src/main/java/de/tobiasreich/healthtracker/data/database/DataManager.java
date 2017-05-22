package de.tobiasreich.healthtracker.data.database;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.BufferedReader;
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

    private Context context;

    private static DataManager Instance;

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

}

