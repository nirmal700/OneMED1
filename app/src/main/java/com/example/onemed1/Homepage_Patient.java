package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Homepage_Patient extends AppCompatActivity {
    TextView textView;
    String Name="",Pid="",mPid="",mName="";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Login Info");
        textView = findViewById(R.id.text_view_name);
        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.USER_MAIL);
        mRef.orderByChild("Email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (@NotNull DataSnapshot snap : snapshot.getChildren()) {
                        Pid = snap.child("PatientID").getValue(String.class);
                        Name = snap.child("FullName").getValue(String.class);
                    Toast.makeText(Homepage_Patient.this, "" + Pid, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Homepage_Patient.this, "" + Name, Toast.LENGTH_SHORT).show();
                       if(Pid!=null&&Name!=null)
                        {
                           mPid = Pid;
                           mName=Name;
                            textView.setText("Hi! Mr.  " +mName + "P ID:" + Pid);
                       }
                    }
                }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}