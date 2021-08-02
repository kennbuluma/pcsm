package com.csm.projectcsm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csm.projectcsm.R;
import com.csm.projectcsm.data.entity.Facility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FacilitiesAdapter extends RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder>{
    private ArrayList<Facility> facilities;
    private Context context;

    public FacilitiesAdapter(Context context, ArrayList<Facility> facilities){
        this.context = context;
        this.facilities = facilities;
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
        holder.crd_facility_item.setOnClickListener();
    }
    @Override
    public int getItemCount() {
        if(null==facilities) return 0;
        return facilities.size();
    }

    class FacilityViewHolder extends RecyclerView.ViewHolder{
        public FacilityViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
