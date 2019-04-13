package com.example.esha.personalhealthrecord.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class RecordProvider extends ContentProvider {

    private static final int RECORDS = 1;
    private static final int RECORDS_ID = 2;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    private RecordHelper rHelper;

    static {
        matcher.addURI(ReportContract.AUTHORITY, ReportContract.PATH, RECORDS);
        matcher.addURI(ReportContract.AUTHORITY, ReportContract.PATH+"/#", RECORDS_ID);
    }
    @Override
    public boolean onCreate() {
        rHelper = new RecordHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection,String selection,String[] selectionArgs,String sortOrder) {
        Cursor mCursor;
        SQLiteDatabase database = rHelper.getReadableDatabase();

        int matchUri = matcher.match(uri);
        switch (matchUri) {
            case RECORDS:
                mCursor = database.query(ReportContract.DB_TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case RECORDS_ID:
                selection = ReportContract.RECORD_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                mCursor = database.query(ReportContract.DB_TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Error:  " + uri);
        }


        mCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return mCursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase database = rHelper.getWritableDatabase();

        long id = database.insert(ReportContract.DB_TABLE_NAME, null, values);
        if (id == -1) {
            Log.e("RecordProvider", "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection,String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
