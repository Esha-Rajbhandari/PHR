package com.example.esha.personalhealthrecord.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.esha.personalhealthrecord.R;
import com.example.esha.personalhealthrecord.data.pressuredata.BloodPressureContract;

public class PressureCursorAdapter extends CursorAdapter {
    public PressureCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }
//inflates the layout
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View pressureView = LayoutInflater.from(context).inflate(R.layout.pressure_list_item,parent,false);
        return pressureView;
    }
//binds the view to the layout
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView pressure = view.findViewById(R.id.pressure);
        TextView dateTxt = view.findViewById(R.id.date);

        String sysString = cursor.getString(cursor.getColumnIndexOrThrow(BloodPressureContract.COL_SYSTOLIC));
        String diaString = cursor.getString(cursor.getColumnIndexOrThrow(BloodPressureContract.COL_DIASTOLIC));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(BloodPressureContract.COL_DATE));

        pressure.setText(sysString+"/"+diaString+"mm Hg");
        dateTxt.setText("Date: "+date);
    }
}
