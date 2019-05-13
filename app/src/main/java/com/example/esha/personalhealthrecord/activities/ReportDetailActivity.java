package com.example.esha.personalhealthrecord.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.esha.personalhealthrecord.Adapter.FileAdapter;

import com.example.esha.personalhealthrecord.POJO.PatientRecord;
import com.example.esha.personalhealthrecord.R;
import com.example.esha.personalhealthrecord.data.ReportContract;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private RecyclerView recyclerView;
    private String TAG = "Report Activity";
    private String image;
    private String firstName;
    private String lastName;
    private String age;
    private String gender;
    private String treatmentDate;
    private String medicalTests;
    private String patientFile;
    private static final int PERMISSION_REQUEST_CODE = 1000;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private CoordinatorLayout coordinatorLayout;

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

        firebaseAuth = FirebaseAuth.getInstance();
        checkAuthentication();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        firebaseUser = intent.getStringExtra("uid");
        Log.i(TAG, "onCreate: " + intent);

        coordinatorLayout = findViewById(R.id.coordinatorLayout);

        nameField = findViewById(R.id.name_field);
        ageField = findViewById(R.id.age_field);
        genderField = findViewById(R.id.gender_field);
        dateField = findViewById(R.id.date_field);
        testTextField = findViewById(R.id.test_text_view);

        recyclerView = findViewById(R.id.file_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FileAdapter fileAdapter = new FileAdapter(recyclerView, new ArrayList<String>(), new ArrayList<String>(), getApplicationContext());
        recyclerView.setAdapter(fileAdapter);

        loadData();
    }
//check for authentication
    public void checkAuthentication() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseUser == null) {
                    startActivity(new Intent(ReportDetailActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }
//load the data on real time and display it to the users
    public void loadData() {
        Log.i(TAG, "loadData: " + FieldPath.documentId());
        Query dbRef = firebaseFirestore.collection("patient_record").whereEqualTo(FieldPath.documentId(), firebaseUser.trim());
        dbRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    PatientRecord patientRecord = documentSnapshot.toObject(PatientRecord.class);
                    firstName = patientRecord.getPatient_last_name();
                    lastName = patientRecord.getPatient_first_name();
                    gender = patientRecord.getGender();
                    age = patientRecord.getAge();
                    treatmentDate = patientRecord.getTreatment_date();
                    medicalTests = patientRecord.getMedical_tests();
                    nameField.setText("Name: " + firstName + " " + lastName);
                    ageField.setText("Age: " + age);
                    genderField.setText("Gender: " + gender);
                    dateField.setText("Treatment Date: " + treatmentDate);
                    testTextField.setText(medicalTests);
                    List<String> img = patientRecord.getPatient_file();
                    image = img.get(0);
                    ((FileAdapter) recyclerView.getAdapter()).updateFile("file" + new Random().nextInt((100000 - 1) + 1) + 1, image);
                    // Picasso.with(getApplicationContext()).load(image).into(patientFileField);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if (id == R.id.nav_pressure) {
            Intent intent = new Intent(getApplicationContext(), BloodPressureActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }else if(id == R.id.nav_about){
            displayAbout();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    protected void displayAbout() {
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }

    public void addHistory(View view) {
        ContentValues values = new ContentValues();
        values.put(ReportContract.COL_FIRST_NAME, firstName);
        values.put(ReportContract.COL_LAST_NAME, lastName);
        values.put(ReportContract.COL_AGE, age);
        values.put(ReportContract.COL_GENDER, gender);
        values.put(ReportContract.COL_TREATMENT_DATE, treatmentDate);
        values.put(ReportContract.COL_MEDICAL_TESTS, medicalTests);
        values.put(ReportContract.COL_PATIENT_FILE, image);

        Uri newUri = getContentResolver().insert(ReportContract.CONTENT_URI, values);

        Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "History Added", Snackbar.LENGTH_SHORT);
        snackbar1.show();
    }
}
