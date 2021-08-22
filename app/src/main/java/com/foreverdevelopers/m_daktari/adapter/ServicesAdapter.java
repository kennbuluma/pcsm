package com.foreverdevelopers.m_daktari.adapter;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.m_daktari.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder>{
    private ArrayList<String> services;

    public ServicesAdapter(ArrayList<String> services){
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
        String thisService = services.get(position);
        holder.txServiceItemName.setText(thisService.toUpperCase(Locale.ROOT).trim());
        holder.crdServiceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(SYSTAG, "Service "+thisService);
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
