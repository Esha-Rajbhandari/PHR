package com.example.esha.personalhealthrecord;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class PatientReportAdapter extends FirestoreRecyclerAdapter<PatientRecord, PatientReportAdapter.ReportPlaceHolder> {

    private OnItemClickListener listener;
    private final int NO_POSITION = -1;
    public PatientReportAdapter(FirestoreRecyclerOptions<PatientRecord> mPatientRecordList) {
        super(mPatientRecordList);
    }

    @NonNull
    @Override
    public ReportPlaceHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View reportView =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_report_layout, viewGroup,false);
        return new ReportPlaceHolder(reportView);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReportPlaceHolder holder, int position, @NonNull PatientRecord model) {
        holder.patientTextView.setText(model.getPatient_first_name());
        holder.testTextView.setText(model.getMedical_tests());
    }


    public class ReportPlaceHolder extends RecyclerView.ViewHolder{
        private TextView patientTextView;
        private TextView testTextView;
        public ReportPlaceHolder(@NonNull View itemView) {
            super(itemView);
            patientTextView = itemView.findViewById(R.id.patientTextView);
            testTextView = itemView.findViewById(R.id.testTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
