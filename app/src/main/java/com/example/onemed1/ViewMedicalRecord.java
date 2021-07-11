package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
        Intent intent = getIntent();
        title=intent.getStringExtra(MedicalRecords_Recyler.MEDICAL_RECORD_TITLE);
        String mPatientid=intent.getStringExtra(Homepage_Patient.USER_PATIENT);
        textViewInfoTitle = findViewById(R.id.text_view_medical_records_info_title);
        imageViewInfoImage = findViewById(R.id.image_view_medical_record);
        Log.i("TASC",""+mPatientid);
        dialog.show();
        StorageReference ref = FirebaseStorage.getInstance().getReference("Patient Health Records").child("Shweta Dash100.jpg");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imageViewInfoImage);
                dialog.cancel();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ViewMedicalRecord.this,e.toString(),Toast.LENGTH_LONG).show();
            }
        });
        textViewInfoTitle.setText(title);

    }
}