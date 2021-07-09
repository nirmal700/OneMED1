package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Homepage_activity extends AppCompatActivity {
    TextView textView;
    Button mAddpatient;
    Button mViewpatient;
    Button mUpdelpatient,mPrescribeMed;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Login Info");
        textView = findViewById(R.id.text_view_home_name);
        mAddpatient=findViewById(R.id.ViewMyDetails);
        mViewpatient=findViewById(R.id.Button2);
        mUpdelpatient=findViewById(R.id.Button3);
        mPrescribeMed=findViewById(R.id.Button4);
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.USER_NAME);
//        String mail= intent.getStringExtra(MainActivity.USER_MAIL);
//        mRef.orderByChild("Email").equalTo("cst.20bcta16@gmail.com").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                for (DataSnapshot snap : snapshot.getChildren())
//                {
//                    String pid = snap.child("PatientID").getValue().toString();
//                    Toast.makeText(Homepage_activity.this, ""+pid, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

        textView.setText("Hi! Dr.  " + name);
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
        mUpdelpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_activity.this, Update_Delete_Patient.class);
                startActivity(intent);
            }
        });
        mPrescribeMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_activity.this, Add_Prescription_Organisation.class);
                startActivity(intent);
            }
        });
    }
}