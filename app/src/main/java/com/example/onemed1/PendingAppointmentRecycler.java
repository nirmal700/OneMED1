package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PendingAppointmentRecycler extends AppCompatActivity {


    private RecyclerView recyclerView;
    private PendingAppointmentAdapter adapter;
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_appointment_recycler);
        recyclerView = findViewById(R.id.recycler_view_pending_appointment);
       

        buildRecyclerView();

    }

    private void buildRecyclerView() {
        recyclerView.setHasFixedSize(true);
        Query query = collectionReference.whereEqualTo("doctorName",Homepage_activity.name).whereEqualTo("appointmentDone",false).whereEqualTo("appointmentAccepted",true);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();

        adapter = new PendingAppointmentAdapter(options, new PendingAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onCallClick(String number) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+number));
                startActivity(callIntent);
            }

            @Override
            public void onMessageClick(String number) {
                Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                messageIntent.setData(Uri.parse("sms:"+number));
                startActivity(messageIntent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(PendingAppointmentRecycler.this));
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
