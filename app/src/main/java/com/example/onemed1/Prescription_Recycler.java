package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Prescription_Recycler extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ProgressDialog progressDialog;
    private CollectionReference collectionReference;
    private PrescriptionAdapter mPrescriptionAdapter;
    private List<Prescription> mDatalist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_recycler);
        mDatalist = new ArrayList<>();
        mRecyclerView = findViewById(R.id.RecyclerView);
        collectionReference = FirebaseFirestore.getInstance().collection("Prescriptions");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPrescriptionAdapter = new PrescriptionAdapter(this,mDatalist);
        mRecyclerView.setAdapter(mPrescriptionAdapter);
        Query query = collectionReference.whereEqualTo("id","1001");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot prescribed : task.getResult())
                    {
                        Prescription prescription  = prescribed.toObject(Prescription.class);
                        mDatalist.add(prescription);
                    }
                    mPrescriptionAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}