package com.example.esha.personalhealthrecord;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.annotation.Nullable;

public class NewsDetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView newsTitle;
    private String newsId;
    private TextView newsDesc;
    private ImageView imgView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        setTitle("News");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        newsTitle = findViewById(R.id.news_title_text);
        newsDesc = findViewById(R.id.news_body_text);
        imgView = findViewById(R.id.imageView3);

        Intent intent = getIntent();
         newsId = intent.getStringExtra("uid");

        loadData();
    }

    public void loadData(){
        Query dbRef = firebaseFirestore.collection("news").whereEqualTo(FieldPath.documentId(), newsId);
        dbRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    News news = documentSnapshot.toObject(News.class);
                    newsTitle.setText(news.getNews_title());
                    newsDesc.setText(news.getNews_body());
                    List<String> img = news.getImages();
                    String image = img.get(0);
                    Picasso.with(getApplicationContext()).load(image).into(imgView);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
