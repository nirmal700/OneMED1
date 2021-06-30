package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class View_Patient_Details extends AppCompatActivity {
    Button mFetch;
    TextView mName,mEmail,mBgroup,mBtype,mGender,mDob,mCno,mNid;
    EditText mPid;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_patient_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        mFetch = findViewById(R.id.Fetch);
        mEmail = findViewById(R.id.PatientEmail);
        mBgroup = findViewById(R.id.PatientBgroup);
        mName = findViewById(R.id.PatientName);
        mBtype = findViewById(R.id.PatientBtype);
        mGender = findViewById(R.id.PatientGen);
        mDob = findViewById(R.id.PaitientDob);
        mCno = findViewById(R.id.PatientCno);
        mNid = findViewById(R.id.PatientnationalId);
        mPid = findViewById(R.id.editTextPatientId);
        mFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mpid;
                mpid = mPid.getText().toString();
                if(mpid.isEmpty())
                {
                    mPid.setError("Patient ID is Mandatory");
                    return;
                }
                else
                {
                    mRef =mDatabase.getReference("Patient Info");
                    mRef.child(mpid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String,Object> data = (Map<String, Object>)snapshot.getValue();
                            String name = (String) data.get("Patient Name");
                            String bgroup = (String) data.get("Patient Blood Group");
                            String btype = (String) data.get("Patient Blood Type");
                            String gender = (String) data.get("Patient Gender");
                            String dob = (String) data.get("Patient Date of Birth");
                            String cno = (String) data.get("Patient Contact Number");
                            String mail = (String) data.get("Patient E-mail");
                            String nid = (String) data.get("Patient I-D Card");
                            mName.setText(name);
                            mBgroup.setText(bgroup);
                            mBtype.setText(btype);
                            mGender.setText(gender);
                            mDob.setText(dob);
                            mCno.setText(cno);
                            mEmail.setText(mail);
                            mNid.setText(nid);
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                            mPid.setError("Enter Valid Patient ID");
                            return;
                        }
                    });
                }
            }
        });
    }
}