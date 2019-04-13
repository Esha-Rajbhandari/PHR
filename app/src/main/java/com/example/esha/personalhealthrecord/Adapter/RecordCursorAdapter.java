package com.example.esha.personalhealthrecord.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.esha.personalhealthrecord.R;
import com.example.esha.personalhealthrecord.data.ReportContract;

public class RecordCursorAdapter extends CursorAdapter {
    public RecordCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.record_list_item, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView firstName = view.findViewById(R.id.patientTextView);
        TextView medicalTests = view.findViewById(R.id.testTextView);

        int nameColIndex = cursor.getColumnIndex(ReportContract.COL_FIRST_NAME);
        int testColIndex = cursor.getColumnIndex(ReportContract.COL_MEDICAL_TESTS);

        String name = cursor.getString(nameColIndex);
        String tests = cursor.getString(testColIndex);

        firstName.setText(name);
        medicalTests.setText(tests);
    }
}
