package com.foreverdevelopers.m_daktari.adapter;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_SERVICE;
import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.ActivePath;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private final ArrayList<String> services;
    private final AppViewModel viewModel;
    private final Integer currentIndex;
    private final NavController navController;
    private final HashMap<Integer, ActivePath> activePathMap;

    public ServicesAdapter(AppViewModel viewModel,
                           ArrayList<String> services,
                           Integer currentIndex,
                           NavController navController,
                           HashMap<Integer, ActivePath> activePathMap){
        this.services = services;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.navController = navController;
        this.activePathMap = activePathMap;
    }

    @NonNull
    @NotNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_service, parent, false);
        return new ServicesAdapter.ServiceViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceViewHolder holder, int position) {
        if(null==services) return;
        String thisService = services.get(position);
        holder.txServiceItemName.setText(thisService.toUpperCase(Locale.ROOT).trim());
        holder.crdServiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer nextIndex = currentIndex + 1;
                ActivePath path = activePathMap.get(nextIndex);
                switch (path.remoteAction){
                    case RA_COUNTIES_BY_SERVICE:
                        navController.navigate(R.id.nav_counties);
                        break;
                    case RA_DOCTORS_BY_SERVICE:
                        navController.navigate(R.id.nav_doctors);
                }
                viewModel.setActiveBaseItem(thisService);
                viewModel.setCurrentIndex(nextIndex);
                viewModel.setCurrentPath(path);
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
            crdServiceItem = (CardView) itemView.findViewById(R.id.crd_service_item);
            txServiceItemName = (TextView) itemView.findViewById(R.id.tx_service_item_name);
        }
    }
}
