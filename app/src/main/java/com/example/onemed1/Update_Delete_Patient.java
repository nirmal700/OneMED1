package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Update_Delete_Patient extends AppCompatActivity {
    EditText mPid,mEmail,mPname,mCnumber,mDob,mId;
    Button mUpdate, mDobbutton,mFetch,mDelete;
    RadioGroup mBgroup,mBtype,mGender;
    RadioButton rButton,rMale,rFemale,rPos,rNeg,rA,rB,rO,rAb;
    String mBg="",mBt="",mGen="",mDobm="";
    int mDate,mMonth,mYear,f=-1;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference mRef1;
    DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_patient);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDatabase = FirebaseDatabase.getInstance();
        mRef1=mDatabase.getReference("Patient Info");
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
        mUpdate = findViewById(R.id.update);
        mDelete = findViewById(R.id.delete);
        mFetch = findViewById(R.id.FetchUp);
        rMale = findViewById(R.id.male);
        rFemale = findViewById(R.id.female);
        rPos = findViewById(R.id.Positive);
        rNeg = findViewById(R.id.Negative);
        rA = findViewById(R.id.bga);
        rB = findViewById(R.id.bgb);
        rO = findViewById(R.id.bgo);
        rAb = findViewById(R.id.bgab);
        mBgroup = findViewById(R.id.bgid);
        mDobbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar Calendar = java.util.Calendar.getInstance();
                mDate = Calendar.get(java.util.Calendar.DATE);
                mMonth = Calendar.get(java.util.Calendar.MONTH);
                mYear=Calendar.get(java.util.Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(Update_Delete_Patient.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mDobm=dayOfMonth+"/"+(month+1)+"/"+year;
                        mDob.setText(mDobm);
                    }
                },mYear,mMonth,mDate);
                dpd.show();
            }
        });
        mFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mpid;
                mpid = mPid.getText().toString();
                if(mpid.isEmpty())
                {
                    mPid.setError("Patient ID is Mandatory");
                    f=0;
                    return;
                }
                else
                {
                    mRef =mDatabase.getReference("Patient Info");
                    mRef.child(mpid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                            if ( data==null)
                            {
                                mPid.setError("Enter Valid Patient ID");
                                f=0;
                                return;
                            }
                            else
                                {
                                    f=1;
                                String name = (String) data.get("Patient Name");
                                String bgroup = (String) data.get("Patient Blood Group");
                                String btype = (String) data.get("Patient Blood Type");
                                String gender = (String) data.get("Patient Gender");
                                String dob = (String) data.get("Patient Date of Birth");
                                String cno = (String) data.get("Patient Contact Number");
                                String mail = (String) data.get("Patient E-mail");
                                String nid = (String) data.get("Patient I-D Card");
                                mPname.setText(name);
                                mEmail.setText(mail);
                                mCnumber.setText(cno);
                                mDob.setText(dob);
                                mId.setText(nid);
                                if (gender.equals("Male")) {
                                    rMale.setChecked(true);
                                    rFemale.setChecked(false);
                                }
                                if (gender.equals("Female")) {
                                    rMale.setChecked(false);
                                    rFemale.setChecked(true);
                                }
                                if (bgroup.equals("A")) {
                                    rA.setChecked(true);
                                    rB.setChecked(false);
                                    rAb.setChecked(false);
                                    rO.setChecked(false);
                                }
                                if (bgroup.equals("b")) {
                                    rA.setChecked(false);
                                    rB.setChecked(true);
                                    rAb.setChecked(false);
                                    rO.setChecked(false);
                                }
                                if (bgroup.equals("AB")) {
                                    rA.setChecked(false);
                                    rB.setChecked(false);
                                    rAb.setChecked(true);
                                    rO.setChecked(false);
                                }
                                if (bgroup.equals("O")) {
                                    rA.setChecked(false);
                                    rB.setChecked(false);
                                    rAb.setChecked(false);
                                    rO.setChecked(true);
                                }
                                if (btype.equals("Positive")) {
                                    rPos.setChecked(true);
                                    rNeg.setChecked(false);
                                }
                                if (btype.equals("Negetive")) {
                                    rPos.setChecked(false);
                                    rNeg.setChecked(true);
                                }
                            }
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
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_Delete_Patient.this);
                builder.setMessage("Are You Sure You Want to Update")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                                if(mcnumber.length()!=10)
                                {
                                    mCnumber.setError("The Contact No. Should be of 10 Digits");
                                    return;
                                }
                                mDobm=mDob.getText().toString();
                                if (TextUtils.isEmpty(mDobm)) {
                                    mDob.setError("Date of birth is Required.");
                                    return;
                                }
                                mid = mId.getText().toString();
                                if (TextUtils.isEmpty(mid)) {
                                    mId.setError("Patient I-D is Required.");
                                    return;
                                }
                                mRef.child(mpid).child("Patient ID").setValue(mpid);
                                mRef.child(mpid).child("Patient E-mail").setValue(memail);
                                mRef.child(mpid).child("Patient Name").setValue(mpname);
                                mRef.child(mpid).child("Patient Contact Number").setValue(mcnumber);
                                mRef.child(mpid).child("Patient I-D Card").setValue(mid);
                                checkbutton_bg();
                                mRef.child(mpid).child("Patient Blood Group").setValue(mBg);
                                checkbutton_btype();
                                mRef.child(mpid).child("Patient Blood Type").setValue(mBt);
                                checkbutton_gender();
                                mRef.child(mpid).child("Patient Gender").setValue(mGen);
                                mRef.child(mpid).child("Patient Date of Birth").setValue(mDobm);
                                Toast.makeText(Update_Delete_Patient.this, "The data has been Updated Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Update_Delete_Patient.this, Homepage_activity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Update_Delete_Patient.this);
                builder.setMessage("Are You Sure You Want to Delete")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String mPatientid;
                                mPatientid=mPid.getText().toString();
                                if(f==0||f==-1)
                                {
                                    Toast.makeText(Update_Delete_Patient.this, "The Info can't be Deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    mRef1.child(mPid.getText().toString()).setValue(null);
                                    Toast.makeText(Update_Delete_Patient.this, "The Info has been Deleted successfully", Toast.LENGTH_SHORT).show();
                                }
                                Intent intent = new Intent(Update_Delete_Patient.this, Homepage_activity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("Cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
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