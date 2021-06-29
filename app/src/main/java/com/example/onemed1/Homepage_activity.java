package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Homepage_activity extends AppCompatActivity {
    TextView textView;
    Button mAddpatient;
    Button mViewpatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.textView8);
        mAddpatient=findViewById(R.id.addPatient);
        mViewpatient=findViewById(R.id.ViewPatient);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.USER_NAME);
        textView.setText("Hi! Mr.  " + name);
        mAddpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_activity.this, PatientAddinfo.class);
                startActivity(intent);
            }
        });
        mViewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_activity.this, View_Patient_Details.class);
                startActivity(intent);
            }
        });
    }
}