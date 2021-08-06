package com.csm.projectcsm.adapter;

import static com.csm.projectcsm.util.Common.SYSTAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
        Doctor thisDoctor = doctors.get(position);
        holder.txDoctorItemCode.setText(thisDoctor.id);
        holder.txDoctorItemName.setText(thisDoctor.name);
        holder.crdDoctorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(SYSTAG, "Doctor "+thisDoctor.id+": "+thisDoctor.name);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(null==doctors) return 0;
        return doctors.size();
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdDoctorItem;
        public final TextView txDoctorItemCode, txDoctorItemName;
        public DoctorViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdDoctorItem = (CardView) itemView.findViewById(R.id.crd_doctor_item);
            txDoctorItemCode = (TextView) itemView.findViewById(R.id.tx_doctor_item_id);
            txDoctorItemName = (TextView) itemView.findViewById(R.id.tx_doctor_item_name);
        }
    }
}
