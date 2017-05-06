package de.tobiasreich.healthtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by T on 06.05.2017. */
public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HealthTracker.db";
    public static final String TABLE_NAME_SLEEP = "sleep";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DURATION = "DURATION";
    public static final String COLUMN_TIMESTAMP = "TIMESTAMP";
    public static final String COLUMN_QUALITY = "QUALITY";
    public static final String COLUMN_SLEEP_NOTE_NAME = "NOTE";

    public SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME_SLEEP
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DURATION + " REAL, "
                + COLUMN_TIMESTAMP + " INTEGER, "
                + COLUMN_QUALITY + " REAL, "
                + COLUMN_SLEEP_NOTE_NAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /** Inserts the values to the DB.
     *
     * @param duration
     * @param timestamp
     * @param quality
     * @param note
     * @return -1 if error, else the line number that was inserted.
     */
    public long insertData(double duration, long timestamp, float quality, String note){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DURATION, duration);
        contentValues.put(COLUMN_TIMESTAMP, timestamp);
        contentValues.put(COLUMN_QUALITY, quality);
        contentValues.put(COLUMN_SLEEP_NOTE_NAME, note);
        return db.insert(TABLE_NAME_SLEEP, null, contentValues);
    }

}
