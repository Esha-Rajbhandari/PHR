package com.example.esha.personalhealthrecord.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class ReportContract implements BaseColumns {

    public static final String AUTHORITY = "com.example.esha.personalhealthrecord.data";

    public static final String PATH = "records";

    public static final Uri BASE_URI = Uri.parse("content://"+AUTHORITY);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH;

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI, PATH);

   public static  final String DB_TABLE_NAME = "records";

   public static final String RECORD_ID = BaseColumns._ID;

   public static final String COL_FIRST_NAME = "first_name";

   public static final String COL_LAST_NAME = "last_name";

   public static final String COL_AGE = "age";

   public static final String COL_GENDER = "gender";

   public static final String COL_TREATMENT_DATE = "treatment_date";

   public static final String COL_MEDICAL_TESTS = "medical_tests";

   public static final String COL_PATIENT_FILE = "patient_file";

}
