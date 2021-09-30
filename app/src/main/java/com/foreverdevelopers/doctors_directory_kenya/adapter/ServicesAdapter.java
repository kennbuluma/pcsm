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
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private List<Service> services;
    private final AppViewModel viewModel;
    private final int currentIndex;

    public ServicesAdapter(AppViewModel viewModel,
                           List<Service> services,
                           int currentIndex){
        this.services = services;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
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
        if(null==services || null == services.get(position)) return;
        Service thisService = services.get(position);
        holder.txServiceItemName.setText(thisService.name.toUpperCase(Locale.ROOT).trim());
        holder.crdServiceItem.setOnClickListener(v -> {
            viewModel.setCurrentIndexor(new Indexor(currentIndex+1, thisService));
        });
    }
    @Override
    public int getItemCount() {
        return null==services ? 0 : services.size();
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
