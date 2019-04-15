package com.example.esha.personalhealthrecord.activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.esha.personalhealthrecord.R;
import com.example.esha.personalhealthrecord.data.ReportContract;

public class MedicalDetailHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private Uri currentUri;
    private TextView nameField;
    private TextView ageField;
    private TextView genderField;
    private TextView dateField;
    private TextView testTextField;
    private static final int EXISTING_LOADER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        currentUri = intent.getData();

        nameField = findViewById(R.id.name_field);
        ageField = findViewById(R.id.age_field);
        genderField = findViewById(R.id.gender_field);
        dateField = findViewById(R.id.date_field);
        testTextField = findViewById(R.id.test_text_view);
        Button historyBtn = findViewById(R.id.btn_add);
        historyBtn.setVisibility(View.GONE);

        if(currentUri != null){
            getLoaderManager().initLoader(EXISTING_LOADER, null, MedicalDetailHistoryActivity.this);
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ReportContract.RECORD_ID,
                ReportContract.COL_FIRST_NAME,
                ReportContract.COL_LAST_NAME,
                ReportContract.COL_AGE,
                ReportContract.COL_GENDER,
                ReportContract.COL_TREATMENT_DATE,
                ReportContract.COL_MEDICAL_TESTS };

        return new CursorLoader(this,
                ReportContract.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            // Find the columns of pet attributes that we're interested in
            int firstNameIndex = data.getColumnIndex(ReportContract.COL_FIRST_NAME);
            int lastNameIndex = data.getColumnIndex(ReportContract.COL_LAST_NAME);
            int ageIndex = data.getColumnIndex(ReportContract.COL_AGE);
            int genderIndex = data.getColumnIndex(ReportContract.COL_GENDER);
            int treatmentIndex = data.getColumnIndex(ReportContract.COL_TREATMENT_DATE);
            int testIndex = data.getColumnIndex(ReportContract.COL_MEDICAL_TESTS);

            // Extract out the value from the Cursor for the given column index
            String firstName = data.getString(firstNameIndex);
            String lastName = data.getString(lastNameIndex);
            String age = data.getString(ageIndex);
            String gender = data.getString(genderIndex);
            String treatmentDate = data.getString(treatmentIndex);
            String medicalTests = data.getString(testIndex);

            // Update the views on the screen with the values from the database
            nameField.setText("Name: "+firstName+" "+lastName);
            ageField.setText("Age: "+age);
            genderField.setText("Gender: "+gender);
            dateField.setText("Treatment date: "+treatmentDate);
            testTextField.setText(medicalTests);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
