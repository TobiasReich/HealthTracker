/**
 *
 * Code is based on this: https://code.google.com/archive/p/datetimepicker/
 * and related in SO here: http://stackoverflow.com/questions/5975735/android-timepicker-and-datepicker-in-the-same-dialog-box
 *
 * Copyright 2010 Lukasz Szmit <devmail@szmit.eu>
 * Modified 2016 Tobias Reich <tr@b2g.world>
 *
 * <p/>
 * Original code is licensed under the Apache License, Version 2.0 (the "License");
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 */
package de.tobiasreich.healthtracker.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import java.util.Calendar;

import de.tobiasreich.healthtracker.R;

/** View showing Date and Time Picke */
public class DateTimePicker extends RelativeLayout implements View.OnClickListener {

    private final ViewSwitcher viewSwitcher;
    private final DatePicker mDatePicker;
    private final TimePicker mTimePicker;

    public DateTimePicker(Context context) {
        this(context, null);
    }

    public DateTimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateTimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.datetimepicker, this, true);

        final View datePickerView = inflater.inflate(R.layout.datepicker, null);
        final View timePickerView = inflater.inflate(R.layout.timepicker, null);

        mDatePicker = (DatePicker) datePickerView.findViewById(R.id.DatePicker);
        mTimePicker = (TimePicker) timePickerView.findViewById(R.id.TimePicker);

        findViewById(R.id.SwitchToDate).setOnClickListener(this);
        findViewById(R.id.SwitchToTime).setOnClickListener(this);

        viewSwitcher = (ViewSwitcher) this.findViewById(R.id.DateTimePickerVS);
        viewSwitcher.addView(datePickerView, 0);
        viewSwitcher.addView(timePickerView, 1);
    }

    public DatePicker getDatePicker(){
        return mDatePicker;
    }

    public TimePicker getTimePicker(){
        return mTimePicker;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.SwitchToDate) {
            v.setEnabled(false);
            findViewById(R.id.SwitchToTime).setEnabled(true);
            viewSwitcher.showPrevious();

        } else if (v.getId() == R.id.SwitchToTime) {
            v.setEnabled(false);
            findViewById(R.id.SwitchToDate).setEnabled(true);
            viewSwitcher.showNext();
        } else {
            Log.d("","");
        }
    }

    public void setIs24hrView(boolean is24hrView) {
        mTimePicker.setIs24HourView(is24hrView);
    }

    @SuppressLint("WrongConstant")
    public void setViewTime(long defaultTime) {
        Calendar cal=Calendar.getInstance();
        cal.setTimeInMillis(defaultTime);

        mDatePicker.updateDate(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mTimePicker.setHour(cal.get(Calendar.HOUR_OF_DAY));
        } else {
            mTimePicker.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            mTimePicker.setMinute(cal.get(Calendar.MINUTE));
        } else {
            mTimePicker.setCurrentMinute(cal.get(Calendar.MINUTE));
        }
    }
}