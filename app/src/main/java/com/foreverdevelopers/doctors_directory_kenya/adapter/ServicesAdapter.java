package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_SERVICE;

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
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private List<Service> services;
    private final AppViewModel viewModel;
    private final Integer currentIndex;
    private final NavController navController;
    private final HashMap<Integer, ActivePath> activePathMap;

    public ServicesAdapter(AppViewModel viewModel,
                           List<Service> services,
                           Integer currentIndex,
                           NavController navController,
                           HashMap<Integer, ActivePath> activePathMap){
        this.services = services;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.navController = navController;
        this.activePathMap = activePathMap;
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
                Integer nextIndex = currentIndex + 1;
                Objects.requireNonNull(activePathMap.get(nextIndex)).currentPath.data = thisService;
                viewModel.setCurrentIndex(nextIndex);
                navController.navigate(Objects.requireNonNull(activePathMap.get(currentIndex)).nextPath.path);
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
