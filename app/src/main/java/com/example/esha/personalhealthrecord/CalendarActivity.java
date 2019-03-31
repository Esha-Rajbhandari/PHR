package com.example.esha.personalhealthrecord;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.riontech.calendar.CustomCalendar;
import com.riontech.calendar.dao.EventData;
import com.riontech.calendar.dao.dataAboutDate;

import java.util.ArrayList;


public class CalendarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private CustomCalendar customCalendar;
    private EventData mEventData = new EventData();
    private ArrayList<EventData> mCalendarEvent = new ArrayList<>();
    private ArrayList<dataAboutDate> mDataAboutDate = new ArrayList<>();
    private dataAboutDate dataEventAboutDate  = new dataAboutDate();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.calendar_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.calendar_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        customCalendar = (CustomCalendar) findViewById(R.id.customCalendar);

        String[] arr = {"2018-10-10", "2018-10-11", "2018-06-15", "2018-06-16", "2018-06-25"};
        for (int i = 0; i < 3; i++) {
            int eventCount = 1;
            customCalendar.addAnEvent(arr[i], eventCount, getEventDataList(eventCount));
        }
    }

    private ArrayList<EventData> getEventDataList(int eventCount) {
        dataEventAboutDate.setTitle("Birthday");
        dataEventAboutDate.setSubject("Happy Birthday");
        mDataAboutDate.add(dataEventAboutDate);
        mEventData.setSection("Hello");
        mEventData.setData(mDataAboutDate);
         mCalendarEvent.add(mEventData);
        return mCalendarEvent;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
