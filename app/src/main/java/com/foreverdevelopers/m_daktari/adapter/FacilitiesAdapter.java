package com.foreverdevelopers.m_daktari.adapter;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.entity.Facility;

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
        Facility thisFacility = facilities.get(position);
        holder.txFacilityItemCode.setText(thisFacility.code);
        holder.txFacilityItemName.setText(thisFacility.name);
        holder.crdFacilityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w(SYSTAG, "Facility "+thisFacility.code+": "+thisFacility.name);
            }
        });
    }
    @Override
    public int getItemCount() {
        if(null==facilities) return 0;
        return facilities.size();
    }

    class FacilityViewHolder extends RecyclerView.ViewHolder{
        public final CardView crdFacilityItem;
        public final TextView txFacilityItemCode, txFacilityItemName;
        public FacilityViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            crdFacilityItem = (CardView) itemView.findViewById(R.id.crd_facility_item);
            txFacilityItemCode = (TextView) itemView.findViewById(R.id.tx_facility_item_code);
            txFacilityItemName = (TextView) itemView.findViewById(R.id.tx_facility_item_name);
        }
    }
}
