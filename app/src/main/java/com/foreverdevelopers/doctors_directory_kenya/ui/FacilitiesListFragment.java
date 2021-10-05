package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES_BY_COUNTY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.adapter.FacilitiesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.FacilityRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.FacilityViewModel;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FacilitiesListFragment extends Fragment {

    private AppViewModel appViewModel;
    private List<Facility> currentFacilities= new ArrayList<>();
    private FacilitiesAdapter facilitiesAdapter;
    private SharedPreferences sharedPreferences;
    private String nextAction,nextData,prevData;
    private NavController navController;

    public static FacilitiesListFragment newInstance() {
        return new FacilitiesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        FacilityViewModel facilityViewModel = new ViewModelProvider(requireActivity()).get(FacilityViewModel.class);

        sharedPreferences = requireContext().getSharedPreferences("router", Context.MODE_PRIVATE);
        int nextRoute = sharedPreferences.getInt("nextRoute", -1);
        nextAction = sharedPreferences.getString("nextAction","");
        nextData = sharedPreferences.getString("nextData", "");
        int prevRoute = sharedPreferences.getInt("prevRoute", -1);
        String prevAction = sharedPreferences.getString("prevAction", "");
        prevData = sharedPreferences.getString("prevData", "");

        final View root = inflater.inflate(R.layout.fragment_facilities_list, container, false);
        RecyclerView.LayoutManager facilitiesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView facilitiesView = root.findViewById(R.id.rcv_facilities);
        TextView title = root.findViewById(R.id.lbl_facility_list);
        TextInputEditText facilitySearch = root.findViewById(R.id.et_facility_search);
        facilitySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do Nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Do Nothing
            }
        });

        appViewModel.facilityRepo.observe(getViewLifecycleOwner(), new Observer<FacilityRepo>() {
            @Override
            public void onChanged(FacilityRepo facilityRepo) {
                if(null!=facilityRepo){
                    switch(nextAction){
                        case RA_FACILITIES:{
                            facilityRepo.facilities();
                            break;
                        }
                        case RA_FACILITIES_BY_COUNTY:{
                            County county = Converter.stringToCounty(nextData);
                            facilityRepo.facilityByCounty(county.name);
                            break;
                        }
                    }
                }
            }
        });
        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController controller) {
                navController = controller;
            }
        });
        facilityViewModel.filteredFacilities.observe(getViewLifecycleOwner(), new Observer<List<Facility>>() {
            @Override
            public void onChanged(List<Facility> facilities) {
                currentFacilities = facilities;
                if(null==facilities || facilities.size() == 0) return;
                facilitiesAdapter = new FacilitiesAdapter(facilities, nextAction, nextData, prevData, sharedPreferences, navController);
                facilitiesView.setHasFixedSize(true);
                facilitiesView.setLayoutManager(facilitiesLayoutManager);
                facilitiesView.setAdapter(facilitiesAdapter);
            }
        });


        return root;
    }
    private void filter(CharSequence searchValue){
        ArrayList<Facility> facilities = new ArrayList<>();
        for(Facility facility : currentFacilities){
            if(facility.name.toLowerCase(Locale.ROOT).contains(searchValue.toString().toLowerCase(Locale.ROOT))) facilities.add(facility);
        }
        if(!facilities.isEmpty()) facilitiesAdapter.filterFacilities(facilities);
    }
}