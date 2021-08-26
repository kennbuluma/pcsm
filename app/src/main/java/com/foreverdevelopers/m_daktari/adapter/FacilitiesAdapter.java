package com.foreverdevelopers.m_daktari.adapter;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.content.Context;
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

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder>{
    private final ArrayList<String> facilities;
    private final AppViewModel viewModel;
    private final Integer currentIndex;
    private final NavController navController;
    private final HashMap<Integer, ActivePath> activePathMap;

    public FacilitiesAdapter(AppViewModel viewModel,
                             ArrayList<String> facilities,
                             Integer currentIndex,
                             NavController navController,
                             HashMap<Integer, ActivePath> activePathMap){
        this.facilities = facilities;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.navController = navController;
        this.activePathMap = activePathMap;
    }

    @NonNull
    @NotNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_facility, parent, false);
        return new FacilitiesAdapter.FacilityViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull FacilityViewHolder holder, int position) {
        if(null==facilities) return;
        String thisFacility = facilities.get(position);
        holder.txFacilityItemName.setText(thisFacility);
        holder.crdFacilityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer nextIndex = currentIndex + 1;
                ActivePath path = activePathMap.get(nextIndex);
                //viewModel.setActiveBaseItem(thisFacility);
                path.baseItem = thisFacility;
                activePathMap.put(nextIndex, path);
                viewModel.setCurrentIndex(nextIndex);
                viewModel.setCurrentPath(path);
                Log.w(SYSTAG, path.remoteAction.trim());
                if(path.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY)) navController.navigate(R.id.nav_counties);
                if(path.remoteAction.trim().equals(RA_SERVICES_BY_FACILITY)) navController.navigate(R.id.nav_services);
                if(path.remoteAction.trim().equals(RA_DOCTORS_BY_FACILITY)) navController.navigate(R.id.nav_doctors);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(null==facilities) return 0;
        return facilities.size();
    }

    public static class FacilityViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdFacilityItem;
        public final TextView txFacilityItemName;
        public FacilityViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdFacilityItem = (CardView) itemView.findViewById(R.id.crd_facility_item);
            txFacilityItemName = (TextView) itemView.findViewById(R.id.tx_facility_item_name);
        }
    }
}
