package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class ViewMedicalRecord extends AppCompatActivity {
    private TextView textViewInfoTitle;
    private ImageView imageViewInfoImage;
    private String title;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_record);
        dialog = new ProgressDialog(ViewMedicalRecord.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        textViewInfoTitle = findViewById(R.id.text_view_medical_records_info_title);
        imageViewInfoImage = findViewById(R.id.image_view_medical_record);
        Intent intent = getIntent();
        title=intent.getStringExtra(MedicalRecords_Recyler.MEDICAL_RECORD_TITLE);
        String mUrl = intent.getStringExtra(MedicalRecords_Recyler.MEDICAL_RECORD_URL);
        Log.d("URI",""+mUrl);
        if(mUrl!=null)
        {
            Uri myURI = Uri.parse(mUrl);
           imageViewInfoImage.setImageURI(myURI);
            textViewInfoTitle.setText(title);
        }

    }
}