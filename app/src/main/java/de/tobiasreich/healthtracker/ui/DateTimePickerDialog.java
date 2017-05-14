package de.tobiasreich.healthtracker.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import java.util.Calendar;

import de.tobiasreich.healthtracker.R;

/** Dialog opened with the Date-Time-Picker showing a dialog for entering both values */
public class DateTimePickerDialog extends Dialog {

    private final DateTimeListener dateTimeListener;

    private DateTimePicker mDateTimePicker;

    public interface DateTimeListener {
        //void onDateTimeSelected(int year, int month, int day, int hour, int min, long timestamp);
        void onDateTimeSelected(Calendar calendar);
    }

    public DateTimePickerDialog(Context context, final boolean is24HrView, long defaultTime, DateTimeListener listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dateTimeListener = listener;
        setContentView(R.layout.dialog_date_time);
        mDateTimePicker = (DateTimePicker) findViewById(R.id.DateTimePicker);
        mDateTimePicker.setIs24hrView(is24HrView);
        mDateTimePicker.setViewTime(defaultTime);

        // Buttons set & cancel
        findViewById(R.id.SetButton).setOnClickListener(view -> {
            mDateTimePicker.clearFocus();
            setViewsByMillis();
            dismiss();
        });
        findViewById(R.id.CancelButton).setOnClickListener(v -> cancel());
    }

    /** Sets the calendar and it's view elements according to the selected date. */
    @SuppressLint("WrongConstant")
    public void setViewsByMillis(){
        Calendar dateTime = Calendar.getInstance();

        dateTime.set(Calendar.YEAR, mDateTimePicker.getDatePicker().getYear());
        dateTime.set(Calendar.MONTH, mDateTimePicker.getDatePicker().getMonth());
        dateTime.set(Calendar.DAY_OF_MONTH, mDateTimePicker.getDatePicker().getDayOfMonth());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            dateTime.set(Calendar.HOUR_OF_DAY, mDateTimePicker.getTimePicker().getHour());
        } else {
            dateTime.set(Calendar.HOUR_OF_DAY, mDateTimePicker.getTimePicker().getCurrentHour());
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            dateTime.set(Calendar.MINUTE, mDateTimePicker.getTimePicker().getMinute());
        } else {
            dateTime.set(Calendar.MINUTE, mDateTimePicker.getTimePicker().getCurrentMinute());
        }
        dateTimeListener.onDateTimeSelected(dateTime);
    }
}
