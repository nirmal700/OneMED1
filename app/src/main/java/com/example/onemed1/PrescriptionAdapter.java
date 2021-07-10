package com.example.onemed1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.MyViewHolder> {
    private Context mContext;
    private List<Prescription> mDatalist;

    public PrescriptionAdapter(Context mContext, List<Prescription> mDatalist) {
        this.mContext = mContext;
        this.mDatalist = mDatalist;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.prescription_item,parent,false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        Prescription prescription = mDatalist.get(position);
        holder.textViewDoctorName.setText(prescription.getDoctorName());
        holder.textViewMedicineName.setText(prescription.getMedicineName());

        if (prescription.isBreakfast()) {
            holder.buttonBreakfast.setVisibility(View.VISIBLE);
        }
        if (prescription.isLunch()) {
            holder.buttonLunch.setVisibility(View.VISIBLE);
        }
        if (prescription.isDinner()) {
            holder.buttonDinner.setVisibility(View.VISIBLE);
        }

        holder.textViewDateStart.setText(prescription.getDateStart());
        holder.textViewDateEnd.setText(prescription.getDateEnd());
    }

    @Override
    public int getItemCount() {
        return mDatalist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
         TextView textViewDoctorName;
         TextView textViewPatientName;
         TextView textViewMedicineName;
         Button buttonBreakfast;
         Button buttonLunch;
         Button buttonDinner;
         TextView textViewDateStart;
         TextView textViewDateEnd;
         TextView textViewDuration;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            textViewDoctorName = itemView.findViewById(R.id.text_view_doctor_prescribed_by);
            textViewPatientName = itemView.findViewById(R.id.text_view_doctor_prescription_patient_name);
            textViewMedicineName = itemView.findViewById(R.id.text_view_doctor_prescription_medicine_name);
            buttonBreakfast = itemView.findViewById(R.id.button_breakfast_doctor_prescription);
            buttonLunch = itemView.findViewById(R.id.button_lunch_doctor_prescription);
            buttonDinner = itemView.findViewById(R.id.button_dinner_doctor_prescription);
            textViewDateStart = itemView.findViewById(R.id.text_view_date_start_doctor_prescription);
            textViewDateEnd = itemView.findViewById(R.id.text_view_date_end_doctor_prescription);
            textViewDuration = itemView.findViewById(R.id.text_view_item_medicine_duration_doctor_prescription);
        }
    }
}
