package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PatientAddinfo extends AppCompatActivity {
    private EditText mPid,mEmail,mPname,mCnumber,mDob,mId;
    private Button mSave, mDobbutton;
    private RadioGroup mBgroup,mBtype,mGender;
    private RadioButton rButton;
    private String mBg="",mBt="",mGen="",mDobm="";
    int mDate,mMonth,mYear;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_addinfo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference();
        mPid = findViewById(R.id.patientid);
        mEmail = findViewById(R.id.emailid);
        mPname = findViewById(R.id.patientname);
        mCnumber = findViewById(R.id.contactid);
        mDobbutton=findViewById(R.id.dobbutton);
        mDob = findViewById(R.id.dobid);
        mId = findViewById(R.id.nid);
        mBgroup = findViewById(R.id.bgid);
        mBtype = findViewById(R.id.btid);
        mGender = findViewById((R.id.genderid));
        mSave = findViewById(R.id.saveid);
        mBgroup = findViewById(R.id.bgid);
        mDobbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Calendar = java.util.Calendar.getInstance();
                mDate = Calendar.get(java.util.Calendar.DATE);
                mMonth = Calendar.get(java.util.Calendar.MONTH);
                mYear=Calendar.get(java.util.Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(PatientAddinfo.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDobm=dayOfMonth+"/"+(month+1)+"/"+year;
                        mDob.setText(mDobm);
                    }
                },mYear,mMonth,mDate);
                dpd.show();
            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mpid, memail, mpname, mcnumber, mid;
                mpid = mPid.getText().toString();
                if (TextUtils.isEmpty(mpid)) {
                    mPid.setError("Patient ID is Mandatory.");
                    return;
                }
                memail = mEmail.getText().toString();
                if (TextUtils.isEmpty(memail)) {
                    mEmail.setError("Patient Email is Required.");
                    return;
                }
                mpname = mPname.getText().toString();
                if (TextUtils.isEmpty(mpname)) {
                    mPname.setError("Patient Name is Required.");
                    return;
                }
                mcnumber = mCnumber.getText().toString();
                if (TextUtils.isEmpty(mcnumber)) {
                    mCnumber.setError("Contact Number is Required.");
                    return;
                }
                if (TextUtils.isEmpty(mDobm)) {
                    mDob.setError("Date of birth is Required.");
                    return;
                }
                mid = mId.getText().toString();
                if (TextUtils.isEmpty(mid)) {
                    mId.setError("Patient I-D is Required.");
                    return;
                }
                mRef.child("Patient Info").child(mpid).child("Patient ID").setValue(mpid);
                mRef.child("Patient Info").child(mpid).child("Patient E-mail").setValue(memail);
                mRef.child("Patient Info").child(mpid).child("Patient Name").setValue(mpname);
                mRef.child("Patient Info").child(mpid).child("Patient Contact Number").setValue(mcnumber);
                mRef.child("Patient Info").child(mpid).child("Patient I-D Card").setValue(mid);
                checkbutton_bg();
                mRef.child("Patient Info").child(mpid).child("Patient Blood Group").setValue(mBg);
                checkbutton_btype();
                mRef.child("Patient Info").child(mpid).child("Patient Blood Type").setValue(mBt);
                checkbutton_gender();
                mRef.child("Patient Info").child(mpid).child("Patient Gender").setValue(mGen);
                mRef.child("Patient Info").child(mpid).child("Patient Date of Birth").setValue(mDobm);
                Toast.makeText(PatientAddinfo.this, "The data has been pushed Successfully", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onClick: RadioButton Selected :"+mBg);
                Log.d("TAG", "onClick: RadioButton Selected :"+mBt);
                Intent intent = new Intent(PatientAddinfo.this, Homepage_activity.class);
                startActivity(intent);

            }
        });
    }
    public void checkbutton_bg(){
        int radioid = mBgroup.getCheckedRadioButtonId();
        rButton=findViewById(radioid);
        mBg=rButton.getText().toString();
        //Toast.makeText(this, "Selected Radio Button"+mBg, Toast.LENGTH_SHORT).show();
    }
    public void checkbutton_btype(){
        int radioid = mBtype.getCheckedRadioButtonId();
        rButton=findViewById(radioid);
        mBt=rButton.getText().toString();
        //Toast.makeText(this, "Selected Radio Button"+mBt, Toast.LENGTH_SHORT).show();
    }
    public void checkbutton_gender(){
        int radioid = mGender.getCheckedRadioButtonId();
        rButton=findViewById(radioid);
        mGen=rButton.getText().toString();
        //Toast.makeText(this, "Selected Radio Button"+mGen, Toast.LENGTH_SHORT).show();
    }
}