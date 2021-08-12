package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MedicalRecords_Recyler extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddDocument;
    private MedicalRecordAdapter adapter;
    private ProgressDialog dialog;
    private String mPatientid;
    public static final String MEDICAL_RECORD_TITLE = "com.example.onemed1.username.mrt";
    public static final String MEDICAL_RECORD_URL = "com.example.onemed1.username.url";
    public static final String MEDICAL_RECORD_PID = "com.example.onemed1.username.pid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_records_recyler);
        dialog = new ProgressDialog(MedicalRecords_Recyler.this);
        Intent intent = getIntent();
        mPatientid=intent.getStringExtra(Homepage_Patient.USER_PATIENT);
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);

        recyclerView = findViewById(R.id.recycler_view_medical_records);
        buttonAddDocument = findViewById(R.id.button_add_medical_record);
        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MedicalRecords_Recyler.this, AddMedicalRecordPatient.class);
                intent.putExtra(MEDICAL_RECORD_PID,mPatientid);
                startActivity(intent);
            }
        });

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {


        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Patient Medical Records");
        Query query = collectionReference.whereEqualTo("mPatientID",mPatientid);
        FirestoreRecyclerOptions<UploadMedicalRecords> options = new FirestoreRecyclerOptions.Builder<UploadMedicalRecords>()
                .setQuery(query,UploadMedicalRecords.class)
                .build();

        adapter = new MedicalRecordAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MedicalRecords_Recyler.this));
        dialog.cancel();

        adapter.setOnItemClickListener(new MedicalRecordAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, DocumentSnapshot documentSnapshot) {
                UploadMedicalRecords medicalRecord = documentSnapshot.toObject(UploadMedicalRecords.class);
                if (medicalRecord != null) {
                    String Title = medicalRecord.getmTitle();
                    Intent intent = new Intent(MedicalRecords_Recyler.this, ViewMedicalRecord.class);
                    intent.putExtra(MEDICAL_RECORD_TITLE, Title);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}