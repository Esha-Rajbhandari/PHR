package com.example.esha.personalhealthrecord.data.pressuredata;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BloodPressureContract implements BaseColumns {

    public static final String AUTHORITY = "com.example.esha.personalhealthrecord.data.pressuredata";

    public static final String PATH = "pressure";

    public static final Uri BASE_URI = Uri.parse("content://"+AUTHORITY);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;

    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH);

    public static  final String DB_TABLE_NAME = "pressure_records";

    public static final String RECORD_ID = BaseColumns._ID;

    public static final String COL_SYSTOLIC = "systolic";

    public static final String COL_DIASTOLIC = "diastolic";

    public static final String COL_DATE = "date_measured";
}
