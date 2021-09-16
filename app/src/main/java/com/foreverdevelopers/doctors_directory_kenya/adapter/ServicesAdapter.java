package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_FACILITY;

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
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private List<Service> services;
    private final AppViewModel viewModel;
    private final PathData currentPath;
    private final NavController navController;

    public ServicesAdapter(AppViewModel viewModel,
                           List<Service> services,
                           PathData currentPath,
                           NavController navController){
        this.services = services;
        this.viewModel = viewModel;
        this.currentPath = currentPath;
        this.navController = navController;
    }

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
        if(null==services) return;
        Service thisService = null == services.get(position) ? null : services.get(position);
        holder.txServiceItemName.setText(thisService.name.toUpperCase(Locale.ROOT).trim());
        holder.crdServiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int path = -1;
                String pathAction = null;
                Object data = thisService;
                if(currentPath.remoteAction.trim().equals(RA_SERVICES)){
                    pathAction = RA_COUNTIES_BY_SERVICE;
                    path = R.id.nav_counties;
                }
                if(currentPath.remoteAction.trim().equals(RA_SERVICES_BY_FACILITY) ||
                        currentPath.remoteAction.trim().equals(RA_SERVICES_BY_COUNTY)){
                    pathAction = RA_DOCTORS_BY_SERVICE;
                    path = R.id.nav_doctors;
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
        if(null==services) return 0;
        return services.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdServiceItem;
        public final TextView txServiceItemName;
        public ServiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdServiceItem = (CardView) itemView.findViewById(R.id.crd_item_name);
            txServiceItemName = (TextView) itemView.findViewById(R.id.tx_item_name);
        }
    }
}
