package de.tobiasreich.healthtracker.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;

import java.util.Calendar;

/** Created by TR on 26.01.2016. */
public class MedicineDatePicker extends android.support.v7.widget.AppCompatTextView {
    private final String TAG = getClass().getSimpleName();

    private Calendar chosenDate = Calendar.getInstance();

    public MedicineDatePicker(Context context) {
        super(context);
        initView(context);
    }

    public MedicineDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MedicineDatePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(final Context context){
        updateDateTextView();

        setOnClickListener(v -> {
            @SuppressLint("WrongConstant") DatePickerDialog dpd = new DatePickerDialog(context,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        chosenDate.set(year, monthOfYear, dayOfMonth);
                        updateDateTextView();
                    }, chosenDate.get(Calendar.YEAR), chosenDate.get(Calendar.MONTH), chosenDate.get(Calendar.DAY_OF_MONTH));
            dpd.show();
        });
    }


    /** Updates the Date View */
    @SuppressLint("WrongConstant")
    private void updateDateTextView() {
        setText(String.format("%02d.%02d.%04d", chosenDate.get(Calendar.DAY_OF_MONTH), chosenDate.get(Calendar.MONTH) +1 , chosenDate.get(Calendar.YEAR)));
    }

    public Calendar getChosenDate(){
        return chosenDate;
    }

    public void setDate(Calendar date){
        chosenDate = date;
    }


}
