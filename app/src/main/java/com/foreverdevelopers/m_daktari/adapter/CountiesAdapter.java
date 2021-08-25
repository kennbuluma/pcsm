package com.foreverdevelopers.m_daktari.adapter;

import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_COUNTY;
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

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> {
    private final ArrayList<String> counties;
    private final AppViewModel viewModel;
    private final Integer currentIndex;
    private final NavController navController;
    private final HashMap<Integer, ActivePath> activePathMap;
    public CountiesAdapter(AppViewModel viewModel,
                           ArrayList<String> counties,
                           Integer currentIndex,
                           NavController navController,
                           HashMap<Integer, ActivePath> activePathMap){
        this.counties = counties;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.navController = navController;
        this.activePathMap = activePathMap;
    }

    @NonNull
    @NotNull
    @Override
    public CountiesAdapter.CountyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_county, parent, false);
        return new CountyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull CountiesAdapter.CountyViewHolder holder, int position) {
        if(null==counties) return;
        String thisCounty = counties.get(position);
        holder.txCountyItemName.setText(thisCounty);
        holder.crdCountyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer nextIndex = currentIndex + 1;
                ActivePath path = activePathMap.get(nextIndex);
                switch (path.remoteAction){
                    case RA_SERVICES_BY_COUNTY:
                        navController.navigate(R.id.nav_services);
                        break;
                    case RA_FACILITIES_BY_COUNTY:
                        navController.navigate(R.id.nav_facilities);
                }
                viewModel.setActiveBaseItem(thisCounty);
                viewModel.setCurrentIndex(nextIndex);
                viewModel.setCurrentPath(path);
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
            crdCountyItem = (CardView) itemView.findViewById(R.id.crd_county_item);
            txCountyItemName = (TextView) itemView.findViewById(R.id.tx_county_item_name);
        }
    }
}
