package com.example.onemed1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

public class MedicalRecordAdapter extends FirestoreRecyclerAdapter<UploadMedicalRecords,MedicalRecordAdapter.RecordViewHolder> {

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public MedicalRecordAdapter(@NonNull FirestoreRecyclerOptions<UploadMedicalRecords> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull RecordViewHolder holder, int position, @NonNull @NotNull UploadMedicalRecords medicalRecords) {
        holder.textViewTitle.setText(medicalRecords.getmTitle());
    }

    @NonNull
    @NotNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.medicalrecords_item,parent,false);
        return new RecordViewHolder(view);
    }

    class RecordViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;

        public RecordViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_medical_records_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position, getSnapshots().getSnapshot(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int position, DocumentSnapshot documentSnapshot);
    }
}
