package com.example.esha.personalhealthrecord.data.pressuredata;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.esha.personalhealthrecord.data.RecordHelper;
import com.example.esha.personalhealthrecord.data.ReportContract;

public class BloodPressureProvider extends ContentProvider {

    private static final int BLOOD_PRESSURE = 103;
    private static final int BLOOD_PRESSURE_ID = 104;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private BloodPressureDBHelper bpHelper;

    static {
        matcher.addURI(BloodPressureContract.AUTHORITY, BloodPressureContract.PATH, BLOOD_PRESSURE);
        matcher.addURI(BloodPressureContract.AUTHORITY, BloodPressureContract.PATH+"/#", BLOOD_PRESSURE_ID);
    }
    @Override
    public boolean onCreate() {
        bpHelper = new BloodPressureDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs,String sortOrder) {
        SQLiteDatabase database = bpHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = matcher.match(uri);
        switch (match){
            case BLOOD_PRESSURE:
                cursor=database.query(BloodPressureContract.DB_TABLE_NAME, projection, null, null, null,null,null);
                break;
            case BLOOD_PRESSURE_ID:
                selection = BloodPressureContract._ID+"=?";
                selectionArgs = new String [] {String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(BloodPressureContract.DB_TABLE_NAME, projection,selection,selectionArgs, null, null, sortOrder);
                break;
            default:
                new IllegalArgumentException("Unknown URI");
        }
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        final int match = matcher.match(uri);
        switch (match) {
            case BLOOD_PRESSURE:
                return BloodPressureContract.CONTENT_TYPE;
            case BLOOD_PRESSURE_ID:
                return BloodPressureContract.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Override
    public Uri insert(Uri uri,  ContentValues values) {
        final int match = matcher.match(uri);
        switch (match) {
            case BLOOD_PRESSURE:
                return savePressure(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri savePressure(Uri uri, ContentValues values) {
        String systolic = values.getAsString(BloodPressureContract.COL_SYSTOLIC);
        String diastolic = values.getAsString(BloodPressureContract.COL_DIASTOLIC);
        String date = values.getAsString(BloodPressureContract.COL_DATE);
        if(systolic == null || diastolic == null){
            throw new IllegalArgumentException();
        }

        SQLiteDatabase database = bpHelper.getWritableDatabase();
        long id=database.insert(BloodPressureContract.DB_TABLE_NAME,null,values);
        return ContentUris.withAppendedId(uri,id);
    }


    @Override
    public int delete(Uri uri,  String selection, String[] selectionArgs) {
        SQLiteDatabase database = bpHelper.getWritableDatabase();

        final int match = matcher.match(uri);
        switch (match) {
            case BLOOD_PRESSURE:
                // Delete all rows that match the selection and selection args
                return database.delete(BloodPressureContract.DB_TABLE_NAME, selection, selectionArgs);
            case BLOOD_PRESSURE_ID:
                // Delete a single row given by the ID in the URI
                selection = BloodPressureContract._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return database.delete(BloodPressureContract.DB_TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        final int match = matcher.match(uri);
        switch (match){
            case BLOOD_PRESSURE:
                return updatePressure(uri, values, selection, selectionArgs);
            case BLOOD_PRESSURE_ID:
                selection = BloodPressureContract._ID + "=?";
                selectionArgs = new String[]{
                        String.valueOf(ContentUris.parseId(uri))
                };
                return updatePressure(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not suppotted");
        }
    }

    private int updatePressure(Uri uri, ContentValues values, String selection, String[] selectionArgs){
        String systolic = values.getAsString(BloodPressureContract.COL_SYSTOLIC);
        String diastolic = values.getAsString(BloodPressureContract.COL_DIASTOLIC);
        String date = values.getAsString(BloodPressureContract.COL_DATE);
        if (values.containsKey(BloodPressureContract.COL_SYSTOLIC)) {
            systolic = values.getAsString(BloodPressureContract.COL_SYSTOLIC);
            if (systolic == null) {
                throw new IllegalArgumentException("Systolic pressure is required");
            }
        }

        if (values.containsKey(BloodPressureContract.COL_DIASTOLIC)) {
            diastolic = values.getAsString(BloodPressureContract.COL_DIASTOLIC);
            if (diastolic == null) {
                throw new IllegalArgumentException("diastolic pressure is required");
            }
        }

        if (values.containsKey(BloodPressureContract.COL_DATE)) {
            date = values.getAsString(BloodPressureContract.COL_DATE);
            if (date == null) {
                throw new IllegalArgumentException("Date is required");
            }
        }

        if (values.size() == 0) {
            return 0;
        }
        SQLiteDatabase database = bpHelper.getWritableDatabase();
        long id=database.update(BloodPressureContract.DB_TABLE_NAME,values,selection,selectionArgs);
        return (int) id;
    }
}
