package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;
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
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private List<Service> services;
    private final SharedPreferences sharedPreferences;
    private final NavController navController;
    private final String currentAction, currentData;
    public ServicesAdapter(List<Service> services,
                           String currentAction,
                           String currentData,
                           SharedPreferences sharedPreferences,
                           NavController navController
    ){
        this.services = services;
        this.currentAction = currentAction;
        this.currentData = currentData;
        this.sharedPreferences = sharedPreferences;
        this.navController = navController;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterServices(ArrayList<Service> services){
        this.services = services;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_name, parent, false);
        return new ServicesAdapter.ServiceViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceViewHolder holder, int position) {
        if(null==services || null == services.get(position)) return;
        Service thisService = services.get(position);
        holder.txServiceItemName.setText(thisService.name.toUpperCase(Locale.ROOT).trim());
        holder.crdServiceItem.setOnClickListener(v -> {
            String nextAction = "", nextData = Converter.objectToString(thisService);
            int nextPath = -1;
            switch (currentAction){
                case RA_SERVICES:{
                    nextAction = RA_COUNTIES_BY_SERVICE;
                    nextPath = R.id.nav_counties;
                    break;
                }
                case RA_SERVICES_BY_COUNTY:
                case RA_SERVICES_BY_FACILITY: {
                    nextAction = RA_DOCTORS_BY_SERVICE;
                    nextPath = R.id.nav_doctors;
                    break;
                }
            }
            setPrefData(nextPath, nextAction, nextData);
        });
    }
    @Override
    public int getItemCount() {
        return null==services ? 0 : services.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdServiceItem;
        public final TextView txServiceItemName;
        public ServiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdServiceItem = itemView.findViewById(R.id.crd_item_name);
            txServiceItemName = itemView.findViewById(R.id.tx_item_name);
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
