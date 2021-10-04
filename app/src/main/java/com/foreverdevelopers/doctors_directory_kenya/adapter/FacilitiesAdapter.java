package com.foreverdevelopers.doctors_directory_kenya.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder>{
    private List<Facility> facilities;
    private final AppViewModel viewModel;
    private final int currentIndex;

    public FacilitiesAdapter(AppViewModel viewModel,
                             List<Facility> facilities,
                             int currentIndex){
        this.facilities = facilities;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
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
        holder.crdFacilityItem.setOnClickListener(v -> {
            viewModel.setCurrentIndexor(new Indexor(currentIndex+1, thisFacility));
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
            crdFacilityItem = (CardView) itemView.findViewById(R.id.crd_item_name);
            txFacilityItemName = (TextView) itemView.findViewById(R.id.tx_item_name);
        }
    }
}
