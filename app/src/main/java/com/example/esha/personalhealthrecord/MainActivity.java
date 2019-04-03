package com.example.esha.personalhealthrecord;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeView();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

    }

    private void initializeView(){
        edtUsername = findViewById(R.id.edt_txt_username);
        edtPassword = findViewById(R.id.edt_txt_password);
    }

    public void login(View view){

        final String email = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();


        Intent loginIntent = new Intent(this, DashboardActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
