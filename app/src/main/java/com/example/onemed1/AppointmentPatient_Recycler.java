package com.example.onemed1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.jetbrains.annotations.NotNull;

public class AppointmentPatient_Recycler extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton buttonAddAppointment;
    private CollectionReference collectionReference;
    private AppointmentAdapter adapter;
    private ProgressDialog dialog;
    String mAuthMail,pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_patient_recycler);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        dialog.setCancelable(false);

        pid=Homepage_Patient.mPid;

        recyclerView = findViewById(R.id.recycler_view_appointment_list);
        buttonAddAppointment = findViewById(R.id.button_add_appointment);
        buttonAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppointmentPatient_Recycler.this, AppointmentBooking.class);
                startActivity(intent);
            }
        });
        collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        Query query = collectionReference.whereEqualTo("id",pid);
        //Query query = collectionReference.orderBy("appointmentDate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();
        adapter = new AppointmentAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentPatient_Recycler.this));
        recyclerView.setAdapter(adapter);
        dialog.cancel();
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