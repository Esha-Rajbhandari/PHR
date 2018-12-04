package com.example.esha.personalhealthrecord;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycle_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(new CardContentAdapter(this,getCardContent()));

    }

    public ArrayList<CardContent> getCardContent(){
        ArrayList<CardContent> mCardContent =new ArrayList<>();
        mCardContent.add(new CardContent(R.drawable.report, "Report"));
        mCardContent.add(new CardContent(R.drawable.calendar, "Calendar"));
        mCardContent.add(new CardContent(R.drawable.location, "Location"));
        mCardContent.add(new CardContent(R.drawable.alarm, "Alarm"));
        mCardContent.add(new CardContent(R.drawable.news, "News"));
        mCardContent.add(new CardContent(R.drawable.medicine, "Medicine"));
        return mCardContent;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
