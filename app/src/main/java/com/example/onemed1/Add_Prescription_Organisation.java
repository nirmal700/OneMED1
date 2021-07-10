package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class Add_Prescription_Organisation extends AppCompatActivity {
    private Spinner spinnerNames;
    private NumberPicker numberPickerDuration;
    private EditText editTextMedicineName,mEditPatientId;
    private CheckBox checkBoxBreakfast;
    private CheckBox checkBoxLunch;
    private CheckBox checkBoxDinner;
    private ImageView imageViewDateStart,imageViewDateEnd;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private String authNumber;
    private Button mSave;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription_organisation);
//        FirebaseFirestore.getInstance().collection("Doctor's")
//                .document("Doctor's Info").get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot document = task.getResult();
//                        List<String> group = (List<String>) document.get("Name");
//                    }
//                });
        spinnerNames = findViewById(R.id.spinner_doctors_list);
        String[] names = this.getResources().getStringArray(R.array.doctors_name);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        names); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_spinner_dropdown_item);
        spinnerNames.setAdapter(spinnerArrayAdapter);
        numberPickerDuration = findViewById(R.id.number_picker_duration);
        numberPickerDuration.setMinValue(1);
        numberPickerDuration.setMaxValue(50);
        editTextMedicineName = findViewById(R.id.edit_text_medicine_name);
        int dd,mm,yy;
        checkBoxBreakfast = findViewById(R.id.checkbox_breakfast);
        checkBoxLunch = findViewById(R.id.checkbox_lunch);
        checkBoxDinner = findViewById(R.id.checkbox_dinner);
        imageViewDateStart = findViewById(R.id.image_view_date_start);
        textViewDateStart = findViewById(R.id.text_view_add_date_start);
        textViewDateEnd = findViewById(R.id.text_view_add_date_end);
        imageViewDateEnd=findViewById(R.id.image_view_date_end);
        mEditPatientId=findViewById(R.id.editTextPatientId);
        mSave=findViewById(R.id.Save);
        imageViewDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mDate, mMonth, mYear;
                final Calendar Calendar = java.util.Calendar.getInstance();
                mDate = Calendar.get(java.util.Calendar.DATE);
                mMonth = Calendar.get(java.util.Calendar.MONTH);
                mYear = Calendar.get(java.util.Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(Add_Prescription_Organisation.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mDobm = dayOfMonth + "/" + (month + 1) + "/" + year;
                        textViewDateStart.setText(mDobm);
                    }
                }, mYear, mMonth, mDate);
                dpd.show();
            }
        });
        imageViewDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mDate, mMonth, mYear;
                final Calendar Calendar = java.util.Calendar.getInstance();
                mDate = Calendar.get(java.util.Calendar.DATE);
                mMonth = Calendar.get(java.util.Calendar.MONTH);
                mYear = Calendar.get(java.util.Calendar.YEAR);
                DatePickerDialog dpd = new DatePickerDialog(Add_Prescription_Organisation.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mDobm = dayOfMonth + "/" + (month + 1) + "/" + year;
                        textViewDateEnd.setText(mDobm);
                    }
                }, mYear, mMonth, mDate);
                dpd.show();
            }
        });
        numberPickerDuration.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

            }
        });
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(Add_Prescription_Organisation.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                progressDialog.setCancelable(false);
                String doctorName = spinnerNames.getSelectedItem().toString();
                String medicineName = editTextMedicineName.getText().toString().trim() + "";
                String mPid = mEditPatientId.getText().toString();
                boolean breakfast = checkBoxBreakfast.isChecked();
                boolean lunch = checkBoxLunch.isChecked();
                boolean dinner = checkBoxDinner.isChecked();
                String dateStart = textViewDateStart.getText().toString() + "";
                String dateEnd = textViewDateEnd.getText().toString() + "";
                mDatabase=FirebaseDatabase.getInstance();
                mRef =mDatabase.getReference("Patient Info");
                mRef.child(mPid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                        if (data == null) {
                            mEditPatientId.setError("Enter valid patient ID");
                            return;
                        } else {
                            String name = (String) data.get("Patient Name");
                            Prescription prescription = new Prescription(mPid, doctorName, medicineName, breakfast, lunch, dinner, dateStart, dateEnd, 10, name);
                            CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Prescriptions");
                            collectionReference.add(prescription).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    progressDialog.cancel();
                                    Toast.makeText(Add_Prescription_Organisation.this, "Prescription Added", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(Add_Prescription_Organisation.this, "Prescription Failed" + e, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        mEditPatientId.setError("Enter Valid Patient ID");
                        return;
                    }
                });
            }
        });
    }
}
