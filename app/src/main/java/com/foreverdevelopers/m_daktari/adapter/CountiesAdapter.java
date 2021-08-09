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
import com.foreverdevelopers.m_daktari.data.entity.County;

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
        County thisCounty = counties.get(position);
        holder.txCountyItemCode.setText(thisCounty.code);
        holder.txCountyItemName.setText(thisCounty.name);
        holder.crdCountyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(SYSTAG, "County "+thisCounty.code+": "+thisCounty.name);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(null==counties) return 0;
        return counties.size();
    }

    class CountyViewHolder extends RecyclerView.ViewHolder {
        public final CardView crdCountyItem;
        public final TextView txCountyItemCode, txCountyItemName;
        public CountyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdCountyItem = (CardView) itemView.findViewById(R.id.crd_county_item);
            txCountyItemCode = (TextView) itemView.findViewById(R.id.tx_county_item_code);
            txCountyItemName = (TextView) itemView.findViewById(R.id.tx_county_item_name);
        }
    }
}
