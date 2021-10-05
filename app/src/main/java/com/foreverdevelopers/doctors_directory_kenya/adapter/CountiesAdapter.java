package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> {
    private List<County> counties;
    private final SharedPreferences sharedPreferences;
    private final NavController navController;
    private final String currentAction, currentData;
    public CountiesAdapter(List<County> counties,
                           String currentAction,
                           String currentData,
                           SharedPreferences sharedPreferences,
                           NavController navController
    ){
        this.counties = counties;
        this.currentAction = currentAction;
        this.currentData = currentData;
        this.sharedPreferences = sharedPreferences;
        this.navController = navController;
    }
    public void filterCounties(ArrayList<County> counties){
        this.counties = counties;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CountiesAdapter.CountyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_name, parent, false);
        return new CountyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull CountiesAdapter.CountyViewHolder holder, int position) {
        if(null==counties) return;
        County thisCounty = counties.get(position);
        holder.txCountyItemName.setText(thisCounty.name);
        holder.crdCountyItem.setOnClickListener(v -> {
            String nextAction = "", nextData = Converter.objectToString(thisCounty);
            int nextPath = -1;
            switch (currentAction){
                case RA_COUNTIES:
                case RA_COUNTIES_BY_SERVICE: {
                    nextAction = RA_FACILITIES_BY_COUNTY;
                    nextPath = R.id.nav_facilities;
                    break;
                }
                case RA_COUNTIES_BY_FACILITY:{
                    nextAction = RA_SERVICES_BY_COUNTY;
                    nextPath = R.id.nav_services;
                    break;
                }
            }
            setPrefData(nextPath, nextAction, nextData);
        });
    }
    @Override
    public int getItemCount() {
        return null==counties ? 0 : counties.size();
    }

    public static class CountyViewHolder extends RecyclerView.ViewHolder {
        public final CardView crdCountyItem;
        public final TextView txCountyItemName;
        public CountyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdCountyItem = (CardView) itemView.findViewById(R.id.crd_item_name);
            txCountyItemName = (TextView) itemView.findViewById(R.id.tx_item_name);
        }
    }

    private void setPrefData(int nextRoute, String nextAction, String nextData){
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putInt("nextRoute", nextRoute);
        sharedEditor.putString("nextAction",nextAction);
        sharedEditor.putString("nextData", nextData);
        sharedEditor.putInt("prevRoute", R.id.nav_counties);
        sharedEditor.putString("prevAction",currentAction);
        sharedEditor.putString("prevData", currentData);
        if(!sharedEditor.commit() || null==navController) return;
        navController.navigate(nextRoute < 0 ? R.id.nav_home : nextRoute);
    }
}
