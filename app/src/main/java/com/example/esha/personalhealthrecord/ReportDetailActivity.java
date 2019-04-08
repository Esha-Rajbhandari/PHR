package com.example.esha.personalhealthrecord;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class ReportDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String firebaseUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private DocumentReference dbRef;
    private TextView nameField;
    private TextView ageField;
    private TextView genderField;
    private TextView dateField;
    private TextView testTextField;
    private String TAG = "Report Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        setTitle("Report");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        firebaseUser = intent.getStringExtra("uid");

        nameField = findViewById(R.id.name_field);
        ageField = findViewById(R.id.age_field);
        genderField = findViewById(R.id.gender_field);
        dateField = findViewById(R.id.date_field);
        testTextField = findViewById(R.id.test_text_view);

        loadData();

    }

    public void loadData(){
        Query dbRef = firebaseFirestore.collection("patient_record").whereEqualTo("patient_id", firebaseUser.trim());
        dbRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    PatientRecord patientRecord = documentSnapshot.toObject(PatientRecord.class);
                    nameField.setText("Name: "+patientRecord.getPatient_first_name() + " " + patientRecord.getPatient_last_name());
                    ageField.setText("Age: "+patientRecord.getAge());
                    genderField.setText("Gender: "+patientRecord.getGender());
                    dateField.setText("Treatment Date: "+patientRecord.getTreatment_date());
                    testTextField.setText(patientRecord.getMedical_tests());
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
