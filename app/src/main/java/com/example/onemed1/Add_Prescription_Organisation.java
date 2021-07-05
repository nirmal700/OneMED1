package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Prescription_Organisation extends AppCompatActivity {
    private Spinner spinnerNames;
    private NumberPicker numberPickerDuration;
    private EditText editTextMedicineName;
    private CheckBox checkBoxBreakfast;
    private CheckBox checkBoxLunch;
    private CheckBox checkBoxDinner;
    private ImageView imageViewDateStart,imageViewDateEnd;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private String authNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription_organisation);
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
    }
}
