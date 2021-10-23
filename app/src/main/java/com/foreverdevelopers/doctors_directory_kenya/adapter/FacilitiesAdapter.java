package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_FACILITY;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder>{
    private List<Facility> facilities;
    private final SharedPreferences sharedPreferences;
    private final NavController navController;
    private final String currentAction, currentData, previousData;

    public FacilitiesAdapter(List<Facility> facilities,
                             String currentAction,
                             String currentData,
                             String previousData,
                             SharedPreferences sharedPreferences,
                             NavController navController
    ){
        this.facilities = facilities;
        this.currentAction = currentAction;
        this.currentData = currentData;
        this.sharedPreferences = sharedPreferences;
        this.navController = navController;
        this.previousData = previousData;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterFacilities(ArrayList<Facility> facilities){
        this.facilities = facilities;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_name, parent, false);
        return new FacilitiesAdapter.FacilityViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull FacilityViewHolder holder, int position) {
        if(null==facilities) return;
        Facility thisFacility = facilities.get(position);
        holder.txFacilityItemName.setText(thisFacility.name);
        holder.crdFacilityItem.setOnClickListener(v -> {
            String nextAction = "", nextData = Converter.objectToString(thisFacility);
            int nextPath = -1;
            switch (currentAction){
                case RA_FACILITIES:{
                    nextAction = RA_COUNTIES_BY_FACILITY;
                    nextPath = R.id.nav_counties;
                    break;
                }
                case RA_FACILITIES_BY_COUNTY:{
                    if(previousData.equals(RA_COUNTIES_BY_SERVICE)){
                        nextAction = RA_DOCTORS_BY_FACILITY;
                        nextPath = R.id.nav_doctors;
                    }else{
                        nextAction = RA_SERVICES_BY_FACILITY;
                        nextPath = R.id.nav_services;
                    }
                    break;
                }
            }
            setPrefData(nextPath, nextAction, nextData);
        });
    }
    @Override
    public int getItemCount() {
        return null==facilities ? 0 : facilities.size();
    }

    public static class FacilityViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdFacilityItem;
        public final TextView txFacilityItemName;
        public FacilityViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdFacilityItem = itemView.findViewById(R.id.crd_item_name);
            txFacilityItemName = itemView.findViewById(R.id.tx_item_name);
        }
    }

    private void setPrefData(int nextRoute, String nextAction, String nextData){
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putInt("nextRoute", nextRoute);
        sharedEditor.putString("nextAction",nextAction);
        sharedEditor.putString("nextData", nextData);
        sharedEditor.putInt("prevRoute", R.id.nav_home);
        sharedEditor.putString("prevAction",currentAction);
        sharedEditor.putString("prevData", currentData);
        if(!sharedEditor.commit() || null==navController) return;
        navController.navigate(nextRoute < 0 ? R.id.nav_home : nextRoute);
    }
}
