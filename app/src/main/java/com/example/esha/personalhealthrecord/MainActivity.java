package com.example.esha.personalhealthrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeView();
    }

    private void initializeView(){
        edtUsername = findViewById(R.id.edt_txt_username);
        edtPassword = findViewById(R.id.edt_txt_password);
    }

    public void login(View view){
        Intent loginIntent = new Intent(this, DashboardActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
