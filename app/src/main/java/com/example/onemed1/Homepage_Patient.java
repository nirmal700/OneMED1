package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Homepage_Patient extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.textView7);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.USER_NAME);
        textView.setText("Hi! Mr.  " + name);
    }
}