package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class Homepage_Patient extends AppCompatActivity {
    TextView textView;
    Button mViewmydetails,mViewMyMeds,mCallAmbulance,mMedicalRecord,mBookApp;
    public static String Name="",Pid="",mPid="",mName="",mAuthMail;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    public static final String USER_PATIENT = "com.example.onemed1.username.pid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            mAuthMail = "cst.20bcta16@gmail.ac.in";
        } else {
            mAuthMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Login Info");
        textView = findViewById(R.id.text_view_name);
        mViewMyMeds=findViewById(R.id.ViewMyMeds);
        mViewmydetails = findViewById(R.id.ViewMyDetails);
        mCallAmbulance=findViewById(R.id.CallAmbulance);
        mBookApp = findViewById(R.id.book_appointment);
        mMedicalRecord = findViewById(R.id.Medical_records);
        Intent intent = getIntent();
        String email = intent.getStringExtra(MainActivity.USER_MAIL);
        mRef.orderByChild("Email").equalTo(mAuthMail).addValueEventListener(new ValueEventListener() {
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
                            textView.setText("Hi! Mr.  " +mName+  "\nPatient ID:" + Pid);
                       }
                    }
                }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        mViewmydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_Patient.this, ViewMyDetailsPatient.class);
                intent.putExtra(USER_PATIENT,Pid);
                startActivity(intent);
            }
        });
        mViewMyMeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_Patient.this, Prescription_Patient_Recycler.class);
                intent.putExtra(USER_PATIENT,Pid);
                startActivity(intent);
            }
        });
        mCallAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    callAmbulance();
                }
            });
        mBookApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Homepage_Patient.this, "Appointment..", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Homepage_Patient.this,AppointmentPatient_Recycler.class);
                startActivity(i);
            }
        });
        mMedicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage_Patient.this, MedicalRecords_Recyler.class);
                intent.putExtra(USER_PATIENT,Pid);
                startActivity(intent);
            }
        });
    }


//    private void callAmbulance() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(Homepage_Patient.this);
//        dialog.setMessage("Are you sure you want to call an ambulance?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent phoneIntent = new Intent(Intent.ACTION_CALL);
//                        phoneIntent.setData(Uri.parse("tel:7008000094"));
//                        Intent callIntent = new Intent(Intent.ACTION_CALL);
//                        callIntent.setData(Uri.parse("tel:0377778888"));
//                        startActivity(phoneIntent);
//                        if (ActivityCompat.checkSelfPermission(Homepage_Patient.this,
//                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            mCallAmbulance.setError("Permisson Granted");
//                            return;
//                        }
//                        startActivity(callIntent);
//                    }
//                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        }).setTitle("Call Ambulance");
//
//        dialog.show();
//    }
}
