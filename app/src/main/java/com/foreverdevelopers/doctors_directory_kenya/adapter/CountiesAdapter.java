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
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountiesAdapter extends RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> {
    private List<County> counties;
    private final AppViewModel viewModel;
    private final int currentIndex;
    private final HashMap<Integer, PathData> pathMap;
    public CountiesAdapter(AppViewModel viewModel,
                           List<County> counties,
                           int currentIndex,
                           HashMap<Integer, PathData> pathMap){
        this.counties = counties;
        this.viewModel = viewModel;
        this.currentIndex = currentIndex;
        this.pathMap = pathMap;
    }
    public void filterCounties(ArrayList<County> counties){
        this.counties = counties;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public CountiesAdapter.CountyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_name, parent, false);
        return new CountyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull CountiesAdapter.CountyViewHolder holder, int position) {
        if(null==counties) return;
        County thisCounty = counties.get(position);
        holder.txCountyItemName.setText(thisCounty.name);
        holder.crdCountyItem.setOnClickListener(v -> {
            viewModel.setCurrentIndexor(new Indexor(currentIndex+1, thisCounty),pathMap);
        });
    }
    @Override
    public int getItemCount() {
        return null==counties ? 0 : counties.size();
    }

    public static class CountyViewHolder extends RecyclerView.ViewHolder {
        public final CardView crdCountyItem;
        public final TextView txCountyItemName;
        public CountyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdCountyItem = (CardView) itemView.findViewById(R.id.crd_item_name);
            txCountyItemName = (TextView) itemView.findViewById(R.id.tx_item_name);
        }
    }
}
