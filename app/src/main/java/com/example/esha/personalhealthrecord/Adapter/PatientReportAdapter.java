package com.example.esha.personalhealthrecord.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.esha.personalhealthrecord.POJO.PatientRecord;
import com.example.esha.personalhealthrecord.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

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
//binds the data to the layout
    @Override
    protected void onBindViewHolder(@NonNull ReportPlaceHolder holder, int position, @NonNull PatientRecord model) {
        holder.patientTextView.setText(model.getMedical_tests());
        holder.testTextView.setText(model.getMedical_diagnosis());
        holder.dateTextView.setText("Date: "+model.getTreatment_date());
    }

//gets th elayout components
    public class ReportPlaceHolder extends RecyclerView.ViewHolder{
        private TextView patientTextView;
        private TextView testTextView;
        private TextView dateTextView;
        public ReportPlaceHolder(@NonNull View itemView) {
            super(itemView);
            patientTextView = itemView.findViewById(R.id.patientTextView);
            testTextView = itemView.findViewById(R.id.testTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);

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
