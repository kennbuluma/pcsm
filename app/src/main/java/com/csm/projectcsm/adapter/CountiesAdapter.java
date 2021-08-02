package com.csm.projectcsm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csm.projectcsm.R;
import com.csm.projectcsm.data.entity.County;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> {
    private ArrayList<County> counties;

    public CountiesAdapter(ArrayList<County> counties){
        this.counties = counties;
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
        holder.crd_county_item.setOnClickListener();
    }
    @Override
    public int getItemCount() {
        if(null==counties) return 0;
        return counties.size();
    }

    class CountyViewHolder extends RecyclerView.ViewHolder {
        public CountyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
