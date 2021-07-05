package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class Add_Prescription_Organisation extends AppCompatActivity {
    private Spinner spinnerNames;
    private NumberPicker numberPickerDuration;
    private EditText editTextMedicineName;
    private CheckBox checkBoxBreakfast;
    private CheckBox checkBoxLunch;
    private CheckBox checkBoxDinner;
    private ImageView imageViewDateStart;
    private TextView textViewDateStart;
    private TextView textViewDateEnd;
    private Calendar calendarDateStart;
    private Calendar calendarDateEnd;
    private String authNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription_organisation);
    }
}