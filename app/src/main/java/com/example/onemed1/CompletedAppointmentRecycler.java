package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CompletedAppointmentRecycler extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CompletedAppointmentAdapter adapter;
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_appointment_recycler);
        recyclerView = findViewById(R.id.recycler_view_completed_appointment);

        buildRecyclerView();
    }

    private void buildRecyclerView() {
        Query query = collectionReference.whereEqualTo("doctorName", Homepage_activity.name).whereEqualTo("appointmentDone",true);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();
        adapter = new CompletedAppointmentAdapter(options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
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
