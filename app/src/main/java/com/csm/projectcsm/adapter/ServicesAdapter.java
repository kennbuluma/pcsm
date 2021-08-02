package com.csm.projectcsm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.csm.projectcsm.R;
import com.csm.projectcsm.data.entity.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private ArrayList<Service> services;

    public ServicesAdapter(ArrayList<Service> services){
        this.services = services;
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
        holder.crd_service_item.setOnClickListener();
    }
    @Override
    public int getItemCount() {
        if(null==services) return 0;
        return services.size();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder{
        public ServiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
