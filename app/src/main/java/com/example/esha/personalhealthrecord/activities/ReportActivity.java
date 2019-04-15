package com.example.esha.personalhealthrecord.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;

import com.example.esha.personalhealthrecord.Adapter.PatientReportAdapter;
import com.example.esha.personalhealthrecord.POJO.PatientRecord;
import com.example.esha.personalhealthrecord.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    private FirebaseDatabase mFirebaseDatabase;
//    private DatabaseReference mDatabaseReference;
    PatientReportAdapter mReportAdapter;
    private RecyclerView recyclerView;
    private ArrayList<PatientRecord>  recordList;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

//        mFirebaseDatabase = FirebaseDatabase.getInstance();
//        mDatabaseReference = mFirebaseDatabase.getReference();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        loadReport();

    }

    public void loadReport(){
        final String firebaseUser = firebaseAuth.getCurrentUser().getUid();
        Log.i("ppp", "loadReport: "+firebaseUser);
        Query dbRef = firebaseFirestore.collection("patient_record").whereEqualTo("patient_id", firebaseUser.trim());
        FirestoreRecyclerOptions<PatientRecord> options =  new FirestoreRecyclerOptions.Builder<PatientRecord>()
                .setQuery(dbRef, PatientRecord.class)
                .build();
        mReportAdapter = new PatientReportAdapter(options);
        recyclerView.setAdapter(mReportAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
       mReportAdapter.setOnItemClickListener(new PatientReportAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
               Intent intent = new Intent(ReportActivity.this, ReportDetailActivity.class);
               intent.putExtra("uid", documentSnapshot.getId());
               startActivity(intent);
               finish();
           }
       });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        mReportAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mReportAdapter.stopListening();
    }
}
