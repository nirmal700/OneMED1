package com.example.onemed1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ApproveAppointmentRecycler extends AppCompatActivity {
    private RecyclerView recyclerViewAppointmentList;
    private ApproveAppointmentAdapter adapter;
    private CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("Appointments");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_appointment_recycler);
        recyclerViewAppointmentList = findViewById(R.id.recycler_view_approve_appointment);
        buildRecyclerView();
    }
    private void buildRecyclerView() {
        Toast.makeText(this, ""+Homepage_activity.name, Toast.LENGTH_SHORT).show();
        Query query = collectionReference.whereEqualTo("appointmentAccepted",false).whereEqualTo("appointmentDone",false).whereEqualTo("doctorName", Homepage_activity.name);
        FirestoreRecyclerOptions<Appointment> options = new FirestoreRecyclerOptions.Builder<Appointment>()
                .setQuery(query,Appointment.class)
                .build();

        adapter = new ApproveAppointmentAdapter(options, new ApproveAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onAcceptClick(int position, final DocumentSnapshot documentSnapshot) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ApproveAppointmentRecycler.this);
                dialog.setTitle("Accept")
                        .setMessage("Are you sure you want to accept this appointment?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = documentSnapshot.getId();
                                Appointment appointment = documentSnapshot.toObject(Appointment.class);
                                collectionReference.document(id).update("appointmentAccepted",true);
                                Toast.makeText(ApproveAppointmentRecycler.this,"Appointment Accepted",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }

            @Override
            public void onRejectClick(int position, final DocumentSnapshot documentSnapshot) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ApproveAppointmentRecycler.this);
                dialog.setTitle("Reject")
                        .setMessage("Are you sure you want to reject this appointment?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String id = documentSnapshot.getId();
                                collectionReference.document(id).delete();
                                Toast.makeText(ApproveAppointmentRecycler.this,"Appointment Rejected",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
        recyclerViewAppointmentList.setHasFixedSize(true);
        recyclerViewAppointmentList.setLayoutManager(new LinearLayoutManager(ApproveAppointmentRecycler.this));
        recyclerViewAppointmentList.setAdapter(adapter);
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