package com.example.esha.personalhealthrecord.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.esha.personalhealthrecord.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionRefillActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private EditText edtFullName;
    private EditText edtAddress;
    private EditText edtContact;
    private EditText edtDiagnosis;
    private EditText edtMedicine;
    private EditText edtMg;
    private EditText edtPrescribedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_refill);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        edtFullName = findViewById(R.id.full_name);
        edtAddress = findViewById(R.id.address);
        edtContact = findViewById(R.id.contact);
        edtDiagnosis = findViewById(R.id.diagnosis);
        edtMedicine = findViewById(R.id.medicine);
        edtMg = findViewById(R.id.dose);
        edtPrescribedBy = findViewById(R.id.prescribed_by);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public void requestRefill(View view) {
        Map<String, Object> prescriptionRefill = new HashMap<>();
        prescriptionRefill.put("fullname", edtFullName.getText().toString().trim());
        prescriptionRefill.put("address", edtAddress.getText().toString().trim());
        prescriptionRefill.put("contact", edtContact.getText().toString().trim());
        prescriptionRefill.put("diagnosis", edtDiagnosis.getText().toString().trim());
        prescriptionRefill.put("medicine", edtMedicine.getText().toString().trim());
        prescriptionRefill.put("dose", edtMg.getText().toString().trim());
        prescriptionRefill.put("prescribed_by", edtPrescribedBy.getText().toString().trim());
        firebaseFirestore.collection("medicine_refill").add(prescriptionRefill).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(PrescriptionRefillActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrescriptionRefillActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
