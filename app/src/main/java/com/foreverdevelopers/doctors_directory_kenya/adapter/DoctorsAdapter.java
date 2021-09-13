package com.foreverdevelopers.doctors_directory_kenya.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    private final ArrayList<Object> doctors;
    private final AppViewModel viewModel;
    private final Integer currentIndex;
    private final NavController navController;
    private final HashMap<Integer, ActivePath> activePathMap;
    public DoctorsAdapter(AppViewModel viewModel,
                          ArrayList<Object> doctors,
                          Integer currentIndex,
                          NavController navController,
                          HashMap<Integer, ActivePath> activePathMap){
        this.doctors = doctors;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.navController = navController;
        this.activePathMap = activePathMap;
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
    public void onBindViewHolder(@NonNull @NotNull DoctorViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(null==doctors) return;
        final Doctor thisDoctor = (doctors.get(position) instanceof Doctor) ?
                (Doctor)doctors.get(position) :
                null;
        final String thisDoctorString = (doctors.get(position) instanceof String) ?
                (String) doctors.get(position) :
                null;
        if(null!=thisDoctor){
            holder.txDoctorItemCounty.setText(thisDoctor.county);
            holder.txDoctorItemFacility.setText(thisDoctor.facility);
            holder.txDoctorItemName.setText(thisDoctor.name);
            holder.imgDoctorPhoto.setImageBitmap(Converter.stringToBitmap(thisDoctor.profilePhoto));
        }
        if(null!=thisDoctorString){
            holder.txDoctorItemName.setText(thisDoctorString);
        }

        holder.crdDoctorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer nextIndex = currentIndex + 1;
                if(doctors.get(position) instanceof Doctor) {
                        Objects.requireNonNull(activePathMap.get(nextIndex)).baseItem = thisDoctor;
                        viewModel.setCurrentIndex(nextIndex);
                        navController.navigate(R.id.nav_doctor_details);
                    }
                if(doctors.get(position) instanceof String) {
                        Doctor doctor = new Doctor();
                        doctor.name = thisDoctorString;
                        Objects.requireNonNull(activePathMap.get(nextIndex)).baseItem = doctor;
                        viewModel.setCurrentIndex(nextIndex);
                        navController.navigate(R.id.nav_doctor_details);
                    }
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
