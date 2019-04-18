package com.example.esha.personalhealthrecord.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;

import com.example.esha.personalhealthrecord.Adapter.CardContentAdapter;
import com.example.esha.personalhealthrecord.POJO.CardContent;
import com.example.esha.personalhealthrecord.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView mRecyclerView;
    private ArrayList<CardContent> mCardContent = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firebaseAuth = FirebaseAuth.getInstance();
        checkAuthentication();

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = findViewById(R.id.recycle_view);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(new CardContentAdapter(this, getCardContent()));
    }

    public void checkAuthentication() {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.i("lll", "onAuthStateChanged: " + firebaseUser);

                if (firebaseUser == null) {
                    startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                    finish();
                }
            }
        };
    }

    public void openActivity(Class newActivity) {
        Intent intent = new Intent(DashboardActivity.this, newActivity);
        startActivity(intent);
    }

    public void openDetail(View view) {
        switch (view.getId()) {
            case R.id.report:
                openActivity(ReportActivity.class);
                break;
            case R.id.calendar:
                openActivity(CalendarActivity.class);
                break;
            case R.id.location:
                openActivity(MapsActivity.class);
                break;
            case R.id.alarm:
                openActivity(MedicalHistoryActivity.class);
                break;
            case R.id.news:
                openActivity(NewsActivity.class);
                break;
            case R.id.medicine:
                openActivity(PrescriptionRefillActivity.class);
                break;
        }
    }

    public ArrayList<CardContent> getCardContent() {
        mCardContent.add(new CardContent(R.drawable.report, "Report", R.id.report));
        mCardContent.add(new CardContent(R.drawable.calendar, "Calendar", R.id.calendar));
        mCardContent.add(new CardContent(R.drawable.location, "Location", R.id.location));
        mCardContent.add(new CardContent(R.drawable.report, "Medical History", R.id.alarm));
        mCardContent.add(new CardContent(R.drawable.news, "News", R.id.news));
        mCardContent.add(new CardContent(R.drawable.medicine, "Medicine", R.id.medicine));
        return mCardContent;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            firebaseAuth.signOut();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("kkk", "onStart: lllll");
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("kkk", "onStart: lllll");
        firebaseAuth.addAuthStateListener(firebaseAuthStateListener);
    }
}
