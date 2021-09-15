package com.foreverdevelopers.doctors_directory_kenya.adapter;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_FACILITY;
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
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder>{
    private List<Facility> facilities;
    private final AppViewModel viewModel;
    private final Integer currentIndex;
    private final NavController navController;
    private final HashMap<Integer, ActivePath> activePathMap;

    public FacilitiesAdapter(AppViewModel viewModel,
                             List<Facility> facilities,
                             Integer currentIndex,
                             NavController navController,
                             HashMap<Integer, ActivePath> activePathMap){
        this.facilities = facilities;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.navController = navController;
        this.activePathMap = activePathMap;
    }

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
        holder.crdFacilityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer nextIndex = currentIndex + 1;
                Objects.requireNonNull(activePathMap.get(nextIndex)).currentPath.data = thisFacility;
                viewModel.setCurrentIndex(nextIndex);
                navController.navigate(Objects.requireNonNull(activePathMap.get(currentIndex)).nextPath.path);
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
            crdFacilityItem = (CardView) itemView.findViewById(R.id.crd_item_name);
            txFacilityItemName = (TextView) itemView.findViewById(R.id.tx_item_name);
        }
    }
}
