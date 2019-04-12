package com.example.esha.personalhealthrecord;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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
    private ImageView patientFileField;
    private String TAG = "Report Activity";
    private String image;
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

        nameField = findViewById(R.id.name_field);
        ageField = findViewById(R.id.age_field);
        genderField = findViewById(R.id.gender_field);
        dateField = findViewById(R.id.date_field);
        testTextField = findViewById(R.id.test_text_view);
        patientFileField = findViewById(R.id.patient_file);

        loadData();

        patientFileField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    imageDownload(getApplicationContext(), image);
            }
        });

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
                    List<String> img = patientRecord.getPatient_file();
                    image = img.get(0);
                    Picasso.with(getApplicationContext()).load(image).into(patientFileField);

                }
            }
        });
    }

    public static void imageDownload(Context ctx, String url){
        Log.i("ppp", "imageDownload: "+url);
        Picasso.with(ctx)
                .load(url)
                .into(getTarget("file"));
    }

    private static Target getTarget(final String url){
        Target target = new Target(){

            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + url+".jpg");
                        try {
                            file.createNewFile();
                            FileOutputStream ostream = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.flush();
                            ostream.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }
                }).start();

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        return target;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
