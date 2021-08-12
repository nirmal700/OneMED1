package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ViewMyDetailsPatient extends AppCompatActivity {
    TextView mName,mEmail,mBgroup,mBtype,mGender,mDob,mCno,mNid;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_details_patient);
        mDatabase = FirebaseDatabase.getInstance();
//        mFetch = findViewById(R.id.Fetch);
        mEmail = findViewById(R.id.PatientEmail);
        mBgroup = findViewById(R.id.PatientBgroup);
        mName = findViewById(R.id.PatientName);
        mBtype = findViewById(R.id.PatientBtype);
        mGender = findViewById(R.id.PatientGen);
        mDob = findViewById(R.id.PaitientDob);
        mCno = findViewById(R.id.PatientCno);
        mNid = findViewById(R.id.PatientnationalId);
        Intent intent = getIntent();
        String PatientID = intent.getStringExtra(Homepage_Patient.USER_PATIENT);
        mRef =mDatabase.getReference("Patient Info");
        mRef.child(PatientID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Map<String,Object> data = (Map<String, Object>)snapshot.getValue();
                if(data==null)
                {
                    Toast.makeText(ViewMyDetailsPatient.this, "Unable to fetch....", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String name = (String) data.get("Patient Name");
                    String bgroup = (String) data.get("Patient Blood Group");
                    String btype = (String) data.get("Patient Blood Type");
                    String gender = (String) data.get("Patient Gender");
                    String dob = (String) data.get("Patient Date of Birth");
                    String cno = (String) data.get("Patient Contact Number");
                    String mail = (String) data.get("Patient E-mail");
                    String nid = (String) data.get("Patient I-D Card");
                    mName.setText("Name : "+name);
                    mBgroup.setText("Blood Group :"+bgroup);
                    mBtype.setText("Blood Type :"+btype);
                    mGender.setText("Gender :"+gender);
                    mDob.setText("Date Of Birth :"+dob);
                    mCno.setText("Contact No. :"+cno);
                    mEmail.setText("E-mail ID :"+mail);
                    mNid.setText("National ID :"+nid);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}