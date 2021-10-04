package com.foreverdevelopers.doctors_directory_kenya.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DoctorsArrayAdapter  extends ArrayAdapter<Doctor> {
    private final List<Doctor> doctors;
    private final List<Doctor> doctorsFiltered;
    private final Context context;
    private final int layoutResource;

    public DoctorsArrayAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> doctors) {
        super(context, resource, doctors);
        this.context = context;
        this.layoutResource = resource;
        this.doctors = new ArrayList<>(doctors);
        this.doctorsFiltered = new ArrayList<>(doctors);
    }

    public int getCount(){
        return null==doctorsFiltered ? 0 : doctorsFiltered.size();
    }
    public Doctor getItem(int position){
        return doctorsFiltered.get(position);
    }
    public long getItemId(int position){
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try{
            if(null == convertView){
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                convertView = inflater.inflate(layoutResource, parent, false);
            }
            Doctor doctor = getItem(position);
            TextView name = (TextView) convertView.findViewById(R.id.tx_item_name);
            name.setText(doctor.name);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((Doctor) resultValue).name;
            }

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                List<Doctor> doctorSuggestion = new ArrayList<>();
                if(null != charSequence){
                    for(Doctor doctor : doctors){
                        if(doctor.name.toLowerCase(Locale.ROOT).contains(charSequence.toString().toLowerCase(Locale.ROOT))){
                            doctorSuggestion.add(doctor);
                        }
                    }
                    filterResults.values = doctorSuggestion;
                    filterResults.count = doctorSuggestion.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                doctorsFiltered.clear();
                if(null!=filterResults && filterResults.count > 0){
                    for(Object object : (List<?>) filterResults.values){
                        if(object instanceof Doctor){
                            doctorsFiltered.add((Doctor) object);
                        }
                    }
                    notifyDataSetChanged();
                }else if(null==charSequence){
                    doctorsFiltered.addAll(doctors);
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
