package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_FACILITY;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.foreverdevelopers.doctors_directory_kenya.adapter.ServicesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.ServiceRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.ServiceViewModel;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ServicesListFragment extends Fragment {
    private AppViewModel appViewModel;
    private List<Service> currentServices = new ArrayList<>();
    private ServicesAdapter servicesAdapter;
    private SharedPreferences sharedPreferences;
    private String nextAction,nextData;
    private NavController navController;

    public static ServicesListFragment newInstance() {
        return new ServicesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        ServiceViewModel serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

        sharedPreferences = requireContext().getSharedPreferences("router", Context.MODE_PRIVATE);
        int nextRoute = sharedPreferences.getInt("nextRoute", -1);
        nextAction = sharedPreferences.getString("nextAction","");
        nextData = sharedPreferences.getString("nextData", "");
        int prevRoute = sharedPreferences.getInt("prevRoute", -1);
        String prevAction = sharedPreferences.getString("prevAction", "");
        String prevData = sharedPreferences.getString("prevData", "");

        final View root  = inflater.inflate(R.layout.fragment_services, container, false);
        RecyclerView.LayoutManager servicesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView servicesView = root.findViewById(R.id.rcv_services);
        TextInputEditText serviceSearch = root.findViewById(R.id.et_service_search);
        serviceSearch.addTextChangedListener(new TextWatcher() {
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

        appViewModel.serviceRepo.observe(getViewLifecycleOwner(), new Observer<ServiceRepo>() {
            @Override
            public void onChanged(ServiceRepo serviceRepo) {
                if(null!=serviceRepo){
                    switch(nextAction){
                        case RA_SERVICES:{
                            serviceRepo.services();
                            break;
                        }
                        case RA_SERVICES_BY_COUNTY:{
                            County county = Converter.stringToCounty(nextData);
                            serviceRepo.serviceByCounty(county.name);
                            break;
                        }
                        case RA_SERVICES_BY_FACILITY:{
                            Facility facility = Converter.stringToFacility(nextData);
                            serviceRepo.serviceByFacility(facility.name);
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
        serviceViewModel.filteredServices.observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                currentServices = services;
                if(null==services || services.size() == 0) return;
                servicesAdapter = new ServicesAdapter(services, nextAction, nextData, sharedPreferences, navController);
                servicesView.setHasFixedSize(true);
                servicesView.setLayoutManager(servicesLayoutManager);
                servicesView.setAdapter(servicesAdapter);
            }
        });


        return root;
    }
    private void filter(CharSequence searchValue){
        ArrayList<Service> services = new ArrayList<>();
        for(Service service : currentServices){
            if(service.name.toLowerCase(Locale.ROOT).contains(searchValue.toString().toLowerCase(Locale.ROOT))) services.add(service);
        }
        if(!services.isEmpty()) servicesAdapter.filterServices(services);
    }
}