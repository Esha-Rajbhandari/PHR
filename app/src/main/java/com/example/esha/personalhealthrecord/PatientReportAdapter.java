package com.example.esha.personalhealthrecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PatientReportAdapter extends RecyclerView.Adapter<PatientReportAdapter.ReportPlaceHolder> {

    private Context mContext;
    private ArrayList<PatientRecord> mPatientRecordList;

    public PatientReportAdapter(Context mContext, ArrayList<PatientRecord> mPatientRecordList) {
        this.mContext = mContext;
        this.mPatientRecordList = mPatientRecordList;
    }

    @NonNull
    @Override
    public ReportPlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View reportView =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_report_layout, viewGroup,false);
        return new ReportPlaceHolder(reportView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportPlaceHolder reportPlaceHolder, int i) {
        reportPlaceHolder.patientTextView.setText(mPatientRecordList.get(i).getPatientFirstName() + " " + mPatientRecordList.get(i).getPatientLastName());
        reportPlaceHolder.testTextView.setText(mPatientRecordList.get(i).getMedicalTests());
    }

    @Override
    public int getItemCount() {
        return mPatientRecordList.size();
    }

    public class ReportPlaceHolder extends RecyclerView.ViewHolder{
        private TextView patientTextView;
        private TextView testTextView;
        public ReportPlaceHolder(@NonNull View itemView) {
            super(itemView);
            patientTextView = itemView.findViewById(R.id.patientTextView);
            testTextView = itemView.findViewById(R.id.testTextView);
        }
    }
}
