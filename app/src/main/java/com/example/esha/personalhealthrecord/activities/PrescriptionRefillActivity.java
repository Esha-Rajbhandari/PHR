package com.example.esha.personalhealthrecord.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.esha.personalhealthrecord.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private LinearLayout linearLayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

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

        firebaseAuth = FirebaseAuth.getInstance();
        checkAuthentication();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        edtFullName = findViewById(R.id.full_name);
        edtAddress = findViewById(R.id.address);
        edtContact = findViewById(R.id.contact);
        edtDiagnosis = findViewById(R.id.diagnosis);
        edtMedicine = findViewById(R.id.medicine);
        edtMg = findViewById(R.id.dose);
        edtPrescribedBy = findViewById(R.id.prescribed_by);
        linearLayout = findViewById(R.id.linearLayout);

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

//send the request refill to the firestore
    public void requestRefill(View view) {
        Map<String, Object> prescriptionRefill = new HashMap<>();
        prescriptionRefill.put("fullname", edtFullName.getText().toString().trim());
        prescriptionRefill.put("address", edtAddress.getText().toString().trim());
        prescriptionRefill.put("contact", edtContact.getText().toString().trim());
        prescriptionRefill.put("diagnosis", edtDiagnosis.getText().toString().trim());
        prescriptionRefill.put("medicine", edtMedicine.getText().toString().trim());
        prescriptionRefill.put("dose", edtMg.getText().toString().trim());
        prescriptionRefill.put("prescribed_by", edtPrescribedBy.getText().toString().trim());
        prescriptionRefill.put("user_id",firebaseAuth.getCurrentUser().getUid());
        firebaseFirestore.collection("medicine_refill").add(prescriptionRefill).
                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(PrescriptionRefillActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                        reset();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PrescriptionRefillActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();
            }
        });

        reset();
    }
    //reset the form
    public void reset() {
        edtFullName.setText("");
        edtAddress.setText("");
        edtContact.setText("");
        edtDiagnosis.setText("");
        edtMedicine.setText("");
        edtMg.setText("");
        edtPrescribedBy.setText("");
    }

    public void checkAuthentication() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseUser == null) {
                    startActivity(new Intent(PrescriptionRefillActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    }
