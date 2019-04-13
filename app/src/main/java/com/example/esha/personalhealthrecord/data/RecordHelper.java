package com.example.esha.personalhealthrecord.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "patient_records";
    private static final int DB_VERSION = 1;

    public RecordHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECORDS_TABLE =  "CREATE TABLE " + ReportContract.DB_TABLE_NAME + " ("
                + ReportContract.RECORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ReportContract.COL_FIRST_NAME + " TEXT NOT NULL, "
                + ReportContract.COL_LAST_NAME + " TEXT NOT NULL, "
                + ReportContract.COL_AGE + " INTEGER NOT NULL, "
                + ReportContract.COL_GENDER + " TEXT NOT NULL, "
                + ReportContract.COL_TREATMENT_DATE + " TEXT NOT NULL, "
                + ReportContract.COL_MEDICAL_TESTS + " TEXT NOT NULL, "
                + ReportContract.COL_PATIENT_FILE + " TEXT NOT NULL);";

        db.execSQL(CREATE_RECORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
