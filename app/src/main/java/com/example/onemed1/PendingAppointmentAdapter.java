package com.example.onemed1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PendingAppointmentAdapter extends FirestoreRecyclerAdapter<Appointment, PendingAppointmentAdapter.PendingHolder> {

    private OnItemClickListener listener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;

    public PendingAppointmentAdapter(@NonNull FirestoreRecyclerOptions<Appointment> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull PendingHolder holder, int i, @NonNull Appointment appointment) {
        holder.textViewPatientName.setText(appointment.getName());
        holder.textViewPatientContact.setText(appointment.getId());
        holder.textViewAppointmentDate.setText(appointment.getAppointmentDate());
        holder.textViewAppointmentTime.setText(appointment.getAppointmentTime());
    }

    @NonNull
    @Override
    public PendingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_appointment_doctor_item,parent,false);
        return new PendingHolder(view);
    }

    class PendingHolder extends RecyclerView.ViewHolder {

        private TextView textViewPatientName;
        private TextView textViewPatientContact;
        private TextView textViewAppointmentDate;
        private TextView textViewAppointmentTime;
        private ImageView imageViewCall;
        private ImageView imageViewMessage;
        private Button mConsulted;

        public PendingHolder(@NonNull View itemView) {
            super(itemView);

            textViewPatientName = itemView.findViewById(R.id.text_view_item_pending_appointment_patient_name);
            textViewPatientContact = itemView.findViewById(R.id.text_view_item_pending_appointment_patient_contact);
            textViewAppointmentDate = itemView.findViewById(R.id.text_view_item_pending_appointment_date);
            textViewAppointmentTime = itemView.findViewById(R.id.text_view_item_pending_appointment_time);
            mConsulted=itemView.findViewById(R.id.consulted);
            imageViewCall = itemView.findViewById(R.id.image_view_call_patient);
            imageViewMessage = itemView.findViewById(R.id.image_view_message_patient);
            imageViewCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase=FirebaseDatabase.getInstance();
                    int position = getAdapterPosition();
                    String number = getSnapshots().getSnapshot(position).toObject(Appointment.class).getId();
                    mRef=mDatabase.getReference("Patient Info");

                    mRef.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            Map<String,Object> data = (Map<String, Object>)snapshot.getValue();
                            String cno = (String) data.get("Patient Contact Number");
                            if (cno != null ) {
                                listener.onCallClick(cno);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                }
            });

            imageViewMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase=FirebaseDatabase.getInstance();
                    int position = getAdapterPosition();
                    String number = getSnapshots().getSnapshot(position).toObject(Appointment.class).getId();
                    mRef=mDatabase.getReference("Patient Info");
                    mRef.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            Map<String,Object> data = (Map<String, Object>)snapshot.getValue();
                            String cno = (String) data.get("Patient Contact Number");
                            if (cno != null ) {
                                listener.onMessageClick(cno);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            });
            mConsulted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onConsulted(position,documentSnapshot);
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onCallClick(String number);
        void onMessageClick(String number);

        void onConsulted(int position, DocumentSnapshot documentSnapshot);
    }
}

