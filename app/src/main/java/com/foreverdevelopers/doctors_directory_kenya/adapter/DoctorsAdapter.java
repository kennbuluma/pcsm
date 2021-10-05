package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTOR_DETAILS;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    private List<Doctor> doctors;
    private final SharedPreferences sharedPreferences;
    private final NavController navController;
    private final String currentAction, currentData;
    private final DoctorViewModel doctorViewModel;
    public DoctorsAdapter(DoctorViewModel doctorViewModel,
                          List<Doctor> doctors,
                          String currentAction,
                          String currentData,
                          SharedPreferences sharedPreferences,
                          NavController navController
    ){
        this.doctors = doctors;
        this.doctorViewModel = doctorViewModel;
        this.currentAction = currentAction;
        this.currentData = currentData;
        this.sharedPreferences = sharedPreferences;
        this.navController = navController;
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
            String nextData = Converter.objectToString(thisDoctor);
            int nextPath = R.id.nav_doctor_details;
            doctorViewModel.setDoctor(thisDoctor);
            setPrefData(nextPath, nextData);
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

    private void setPrefData(int nextRoute, String nextData){
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putInt("nextRoute", nextRoute);
        sharedEditor.putString("nextAction", RA_DOCTOR_DETAILS);
        sharedEditor.putString("nextData", nextData);
        sharedEditor.putInt("prevRoute", R.id.nav_doctor_details);
        sharedEditor.putString("prevAction",currentAction);
        sharedEditor.putString("prevData", currentData);
        if(!sharedEditor.commit() || null==navController) return;
        navController.navigate(nextRoute < 0 ? R.id.nav_doctor_details : nextRoute);
    }
}
