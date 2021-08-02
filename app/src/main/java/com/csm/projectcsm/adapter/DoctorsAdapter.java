package com.csm.projectcsm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csm.projectcsm.R;
import com.csm.projectcsm.data.entity.Doctor;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    private ArrayList<Doctor> doctors;

    public DoctorsAdapter(ArrayList<Doctor> doctors){
        this.doctors = doctors;
    }

    @NonNull
    @NotNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_doctor, parent, false);
        return new DoctorsAdapter.DoctorViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull DoctorViewHolder holder, int position) {
        if(null==doctors) return;
        holder.crd_doctor_item.setOnClickListener();
    }
    @Override
    public int getItemCount() {
        if(null==doctors) return 0;
        return doctors.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder{
        public DoctorViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
