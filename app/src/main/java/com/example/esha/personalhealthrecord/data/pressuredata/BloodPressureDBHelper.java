package com.example.esha.personalhealthrecord.data.pressuredata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class BloodPressureDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "pressure_records";
    private static final int DB_VERSION = 1;
    public BloodPressureDBHelper( Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRESSURE_TABLE =  "CREATE TABLE " + BloodPressureContract.DB_TABLE_NAME + " ("
                + BloodPressureContract.RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BloodPressureContract.COL_SYSTOLIC + " TEXT NOT NULL, "
                + BloodPressureContract.COL_DIASTOLIC + " TEXT NOT NULL, "
                + BloodPressureContract.COL_DATE + " TEXT NOT NULL);";

        db.execSQL(CREATE_PRESSURE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
