package com.example.onemed1;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import static androidx.activity.result.contract.ActivityResultContracts.*;

public class AddMedicalRecordPatient extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST =1;
    private Button mButtonChoose,mUpload;
    private EditText mEditText;
    private ImageView mImageview;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private CollectionReference mCollectionRef;
    private String mPatientid;
    private StorageTask mUploadTask;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medical_record_patient);
        mButtonChoose=findViewById(R.id.button_choose_file_medical_records);
        mImageview = findViewById(R.id.imageView3);
        mStorageRef= FirebaseStorage.getInstance().getReference("Patient Health Records");
        mCollectionRef= FirebaseFirestore.getInstance().collection("Patient Medical Records");
        mEditText=findViewById(R.id.edit_text_add_medical_record_title);
        Intent intent = getIntent();
        mPatientid=intent.getStringExtra(Homepage_Patient.USER_PATIENT);
        mUpload=findViewById(R.id.button_upload_document_medical_records);
        mProgressBar=findViewById(R.id.progressBar2);
        mButtonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(AddMedicalRecordPatient.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    UploadFile();
                }
            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void UploadFile() {
        if(mImageUri!=null)
        {
            StorageReference fileref =mStorageRef.child(mEditText.getText().toString()+""+System.currentTimeMillis()+"."+getFileExtension(mImageUri));
           mUploadTask = fileref.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(AddMedicalRecordPatient.this, "Upload successful", Toast.LENGTH_LONG).show();
                    UploadMedicalRecords upload = new UploadMedicalRecords(mEditText.getText().toString(),
                            taskSnapshot.getUploadSessionUri().toString(),mPatientid);
                    mCollectionRef.add(upload);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                     mProgressBar.setProgress((int) progress);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {

                }
            });
        }
    }

    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            mImageview.setImageURI(mImageUri);
        }
    }
}