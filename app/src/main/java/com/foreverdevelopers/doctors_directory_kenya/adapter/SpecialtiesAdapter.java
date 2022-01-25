package com.foreverdevelopers.doctors_directory_kenya.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpecialtiesAdapter extends RecyclerView.Adapter<SpecialtiesAdapter.SpecialtiesViewHolder> {
    private List<String> specialties;
    public SpecialtiesAdapter(List<String> specialties){
        this.specialties = specialties;
    }

    @NonNull
    @NotNull
    @Override
    public SpecialtiesAdapter.SpecialtiesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_string_item, parent, false);
        return new SpecialtiesAdapter.SpecialtiesViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull SpecialtiesAdapter.SpecialtiesViewHolder holder, int position) {
        if(null==specialties) return;
        String thisItem = specialties.get(position);
        holder.txItemName.setText(thisItem);
    }
    @Override
    public int getItemCount() {
        return null==specialties ? 0 : specialties.size();
    }

    public static class SpecialtiesViewHolder extends RecyclerView.ViewHolder {
        public final TextView txItemName;
        public SpecialtiesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txItemName = itemView.findViewById(R.id.tx_str_item_name);
        }
    }
}
