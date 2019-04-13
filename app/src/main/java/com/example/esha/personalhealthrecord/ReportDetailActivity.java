package com.example.esha.personalhealthrecord;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esha.personalhealthrecord.Adapter.FileAdapter;
import com.example.esha.personalhealthrecord.data.ReportContract;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        Log.i(TAG, "onCreate: "+intent);

        nameField = findViewById(R.id.name_field);
        ageField = findViewById(R.id.age_field);
        genderField = findViewById(R.id.gender_field);
        dateField = findViewById(R.id.date_field);
        testTextField = findViewById(R.id.test_text_view);

        recyclerView = findViewById(R.id.file_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FileAdapter fileAdapter = new FileAdapter(recyclerView,new ArrayList<String>(),new ArrayList<String>(),getApplicationContext());
        recyclerView.setAdapter(fileAdapter);

        loadData();
    }


    public void loadData(){
        Log.i(TAG, "loadData: "+FieldPath.documentId());
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
                    nameField.setText("Name: "+firstName + " " + lastName);
                    ageField.setText("Age: "+ age);
                    genderField.setText("Gender: "+ gender);
                    dateField.setText("Treatment Date: "+ treatmentDate);
                    testTextField.setText(medicalTests);
                    List<String> img = patientRecord.getPatient_file();
                    image= img.get(0);
                    ((FileAdapter)recyclerView.getAdapter()).updateFile("file"+new Random().nextInt((100000 - 1) + 1) + 1, image);
                    // Picasso.with(getApplicationContext()).load(image).into(patientFileField);
                }
            }
        });
    }

//    public static void imageDownload(Context ctx, String url){
//        Log.i("ppp", "imageDownload: "+url);
//        Picasso.with(ctx)
//                .load(url)
//                .into(getTarget("file"));
//    }
//
//    private static Target getTarget(final String url){
//        Target target = new Target(){
//
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url+".pdf");
//                        try {
//                            file.createNewFile();
//                            FileOutputStream ostream = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
//                            ostream.flush();
//                            ostream.close();
//                        } catch (IOException e) {
//                            Log.e("IOException", e.getLocalizedMessage());
//                        }
//                    }
//                }).start();
//
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//        return target;
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
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
    }
}
