package com.foreverdevelopers.doctors_directory_kenya.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    private List<Doctor> doctors;
    private final AppViewModel viewModel;
    private final int currentIndex;
    private final HashMap<Integer, PathData> pathMap;
    public DoctorsAdapter(
            AppViewModel viewModel,
            List<Doctor> doctors,
            int currentIndex,
            HashMap<Integer, PathData> pathMap){
        this.doctors = doctors;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.pathMap = pathMap;
    }

    public void filterDoctors(ArrayList<Doctor> doctors){
        this.doctors = doctors;
        notifyDataSetChanged();
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
        Doctor thisDoctor = null == doctors.get(position) ? null : doctors.get(position);
        if(null==thisDoctor) return;
        holder.txDoctorItemCounty.setText(thisDoctor.county);
        holder.txDoctorItemFacility.setText(thisDoctor.facility);
        holder.txDoctorItemName.setText(thisDoctor.name);
        holder.imgDoctorPhoto.setImageBitmap(Converter.stringToBitmap(thisDoctor.profilePhoto));
        holder.crdDoctorItem.setOnClickListener(v -> {
            viewModel.setCurrentIndexor(new Indexor(currentIndex+1, thisDoctor), pathMap);
        });
    }
    @Override
    public int getItemCount() {
        return null==doctors ?  0 : doctors.size();
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
