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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;

public class ViewMedicalRecord extends AppCompatActivity {
    private TextView textViewInfoTitle;
    private ImageView imageViewInfoImage;
    private String title,mAuthMail;
    private ProgressDialog dialog;
    private FirebaseDatabase mDatabase;
    String mPatientid;
    private DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_record);
        dialog = new ProgressDialog(ViewMedicalRecord.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            mAuthMail = "cst.20bcta16@gmail.ac.in";
        } else {
            mAuthMail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        textViewInfoTitle = findViewById(R.id.text_view_medical_records_info_title);
        imageViewInfoImage = findViewById(R.id.image_view_medical_record);
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Login Info");
        mRef.orderByChild("Email").equalTo(mAuthMail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (@NotNull DataSnapshot snap : snapshot.getChildren()) {
                    mPatientid = snap.child("PatientID").getValue(String.class);
                    Toast.makeText(ViewMedicalRecord.this, "" + mPatientid, Toast.LENGTH_SHORT).show();
                    if(mPatientid!=null)
                    {
                        Log.i("TASC",""+mPatientid);
                        dialog.show();
                        StorageReference ref = FirebaseStorage.getInstance().getReference("Patient Health Records").child(title+""+mPatientid+".jpg");
                        Toast.makeText(ViewMedicalRecord.this, ""+title+""+mPatientid+".jpg", Toast.LENGTH_SHORT).show();
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
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(ViewMedicalRecord.this, "Upload Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}