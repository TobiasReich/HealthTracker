package de.tobiasreich.healthtracker.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.tobiasreich.healthtracker.data.myMedicine.Medicine;

/**
 * Created by T on 06.05.2017. */
public class DataBaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String TAG = DataBaseHelper.class.getSimpleName();


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "HealthTracker.db";

    // Table Names
    private static final String TABLE_MEDICINES = "medicines";
    private static final String TABLE_PRESCRIPTIONS = "prescriptions";
    private static final String TABLE_MEDICINE_DIARY = "medicine_diary";

    // Common column names
    private static final String KEY_ID = "id";

    // MEDICINES Table - column names
    private static final String KEY_MEDICINE_NAME = "name";
    private static final String KEY_MEDICINE_DESCRIPTION = "description";
    private static final String KEY_MEDICINE_AMOUNT = "amount";
    private static final String KEY_MEDICINE_PATH = "path";

    // PRESCRIPTIONS Table - column names
    private static final String KEY_PRESCRIPTIONS_TIMESTAMP = "timestamp";
    private static final String KEY_PRESCRIPTIONS_QUANTITY = "quantity";

    // MEDICINE_DIARY Table - column names
    private static final String KEY_MEDI_DIARY_MEDICINE_ID = "medicine_id";
    private static final String KEY_MEDI_DIARY_TIMESTAMP = "timestamp";


    // Table Create Statements
    // Medicine table create statement
    private static final String CREATE_TABLE_MEDICINE = "CREATE TABLE "
            + TABLE_MEDICINES + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_MEDICINE_NAME + " TEXT,"
            + KEY_MEDICINE_DESCRIPTION + " TEXT,"
            + KEY_MEDICINE_AMOUNT + " INTEGER,"
            + KEY_MEDICINE_PATH + " TEXT)";

    // Prescriptions table create statement
    private static final String CREATE_TABLE_PRESCRIPTIONS = "CREATE TABLE "
            + TABLE_PRESCRIPTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_PRESCRIPTIONS_TIMESTAMP + " INTEGER,"
            + KEY_PRESCRIPTIONS_QUANTITY + " INTEGER)";

    // Diary table create statement
    private static final String CREATE_TABLE_MEDICINE_DIARY = "CREATE TABLE "
            + TABLE_MEDICINE_DIARY + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_MEDI_DIARY_MEDICINE_ID + " INTEGER,"
            + KEY_MEDI_DIARY_TIMESTAMP + " INTEGER)";


    public SQLiteDatabase db;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DataBaseHelper -> Constructor");
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "OnCreate -> Creating database");
        db.execSQL(CREATE_TABLE_MEDICINE);
        db.execSQL(CREATE_TABLE_PRESCRIPTIONS);
        db.execSQL(CREATE_TABLE_MEDICINE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRESCRIPTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICINE_DIARY);

        // create new tables
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    /* Creating a medicine */
    public long insertMedicine(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MEDICINE_NAME, medicine.getMedicineName());
        values.put(KEY_MEDICINE_DESCRIPTION, medicine.getDescription());
        values.put(KEY_MEDICINE_AMOUNT, medicine.getAmount());
        values.put(KEY_MEDICINE_PATH, medicine.getPath());

        long column = db.insert(TABLE_MEDICINES, null, values);

        Log.i(TAG, "Adding medicine to database: " + medicine.name + " @column: " + column);

        return column;
    }


    /** get single Medicine
     *
     * @param medicineID id of the medicine to get
     * @return Medicine */
    public Medicine getMedicine(long medicineID) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MEDICINES + " WHERE " + KEY_ID + " = " + medicineID;

        Log.e(TAG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Medicine medicine = new Medicine();
        medicine.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        medicine.setMedicineName((c.getString(c.getColumnIndex(KEY_MEDICINE_NAME))));
        medicine.setMedicineDescription(c.getString(c.getColumnIndex(KEY_MEDICINE_DESCRIPTION)));
        medicine.setMedicineAmount(c.getInt(c.getColumnIndex(KEY_MEDICINE_AMOUNT)));
        medicine.setMedicinePath(c.getString(c.getColumnIndex(KEY_MEDICINE_PATH)));

        return medicine;
    }

    /** getting all medicines
     *
     * @return List of medicines */
    public List<Medicine> getAllMedicines(boolean orderByName) {
        List<Medicine> todos = new ArrayList<>();

        String selectQuery;
        if (orderByName)
            selectQuery = "SELECT  * FROM " + TABLE_MEDICINES + " ORDER BY " + KEY_MEDICINE_NAME + " ASC";
        else
            selectQuery = "SELECT  * FROM " + TABLE_MEDICINES;

        Log.i(TAG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Medicine medicine = new Medicine();
                medicine.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                medicine.setMedicineName((c.getString(c.getColumnIndex(KEY_MEDICINE_NAME))));
                medicine.setMedicineDescription(c.getString(c.getColumnIndex(KEY_MEDICINE_DESCRIPTION)));
                medicine.setMedicineAmount(c.getInt(c.getColumnIndex(KEY_MEDICINE_AMOUNT)));
                medicine.setMedicinePath(c.getString(c.getColumnIndex(KEY_MEDICINE_PATH)));
                todos.add(medicine);
            } while (c.moveToNext());
        }

        return todos;
    }

    /**
     * Updating a medicine entry
     *
     * @param medicine
     * @return int id of the row that was updated
     */
    public int updateMedicine(Medicine medicine) {
        ContentValues values = new ContentValues();
        values.put(KEY_MEDICINE_NAME, medicine.getMedicineName());
        values.put(KEY_MEDICINE_DESCRIPTION, medicine.getDescription());
        values.put(KEY_MEDICINE_AMOUNT, medicine.getAmount());

        // updating row
        return db.update(TABLE_MEDICINES, values, KEY_ID + " = ?", new String[] { String.valueOf(medicine.getId()) });
    }

}
