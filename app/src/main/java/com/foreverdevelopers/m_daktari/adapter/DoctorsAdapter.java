package com.foreverdevelopers.m_daktari.adapter;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.ui.DoctorsListViewModel;
import com.foreverdevelopers.m_daktari.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    private ArrayList<Doctor> doctors;
    private AppViewModel viewModel;
    private Integer currentIndex;

    public DoctorsAdapter(ArrayList<Doctor> doctors, AppViewModel viewModel, Integer currentIndex){
        this.viewModel = viewModel;
        this.doctors = doctors;
        this.currentIndex = currentIndex;
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
        holder.txDoctorItemCounty.setText(thisDoctor.county);
        holder.txDoctorItemFacility.setText(thisDoctor.facility);
        holder.txDoctorItemName.setText(thisDoctor.name);
        holder.imgDoctorPhoto.setImageBitmap(Converter.stringToBitmap(thisDoctor.profilePhoto));
        holder.crdDoctorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setCurrentIndex(currentIndex);
                viewModel.setActiveBaseItem(thisDoctor.id);
                Log.w(SYSTAG, "Doctor "+thisDoctor.id+": "+thisDoctor.name);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(null==doctors) return 0;
        return doctors.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdDoctorItem;
        public final TextView txDoctorItemCounty,txDoctorItemFacility, txDoctorItemName;
        public final ImageView imgDoctorPhoto;
        public DoctorViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdDoctorItem = (CardView) itemView.findViewById(R.id.crd_doctor_item);
            txDoctorItemName = (TextView) itemView.findViewById(R.id.tx_doctor_item_name);
            txDoctorItemFacility= (TextView) itemView.findViewById(R.id.tx_doctor_item_facility);
            txDoctorItemCounty= (TextView) itemView.findViewById(R.id.tx_doctor_item_county);
            imgDoctorPhoto= (ImageView) itemView.findViewById(R.id.img_doctor_item_photo);
        }
    }
}
