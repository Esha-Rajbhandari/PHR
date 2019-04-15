package com.example.esha.personalhealthrecord.activities;

import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.esha.personalhealthrecord.Adapter.RecordCursorAdapter;
import com.example.esha.personalhealthrecord.R;
import com.example.esha.personalhealthrecord.data.ReportContract;

public class MedicalHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {

    private ListView listView;

    RecordCursorAdapter recordCursorAdapter;

    private static final int RECORD_LOADER = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView = findViewById(R.id.list_view);

        recordCursorAdapter = new RecordCursorAdapter(this, null);
        listView.setAdapter(recordCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MedicalHistoryActivity.this, MedicalDetailHistoryActivity.class);
                Uri recordUri = ContentUris.withAppendedId(ReportContract.CONTENT_URI, id);
                intent.setData(recordUri);
                startActivity(intent);
            }
        });

        getLoaderManager().initLoader(RECORD_LOADER, null, MedicalHistoryActivity.this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i,Bundle bundle) {
        String[] projection = {
                ReportContract.RECORD_ID,
                ReportContract.COL_FIRST_NAME,
                ReportContract.COL_MEDICAL_TESTS };

        return new CursorLoader(this,
                ReportContract.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        recordCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        recordCursorAdapter.swapCursor(null);
    }
}
