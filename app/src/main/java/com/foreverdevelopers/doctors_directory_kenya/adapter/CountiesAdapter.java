package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;

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
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> {
    private List<County> counties;
    private final AppViewModel viewModel;
    private final PathData currentPath;
    private final NavController navController;
    public CountiesAdapter(AppViewModel viewModel,
                           List<County> counties,
                           PathData currentPath,
                           NavController navController){
        this.counties = counties;
        this.viewModel = viewModel;
        this.currentPath = currentPath;
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
        holder.crdCountyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int path = -1;
                String pathAction = null;
                Object data = thisCounty;
               if(currentPath.remoteAction.trim().equals(RA_COUNTIES)){
                   pathAction = RA_FACILITIES_BY_COUNTY;
                    path = R.id.nav_facilities;
               }
               if(currentPath.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY)){
                    pathAction = RA_SERVICES_BY_COUNTY;
                    path = R.id.nav_services;
               }
               if(currentPath.remoteAction.trim().equals(RA_COUNTIES_BY_SERVICE)){
                    pathAction = RA_FACILITIES_BY_COUNTY;
                    path = R.id.nav_facilities;
               }
               viewModel.setCurrentPath(new PathData(pathAction, data, path));
               viewModel.setPreviousPath(currentPath);
                if(path<0) return;
                navController.navigate(path);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(null==counties) return 0;
        return counties.size();
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
}
