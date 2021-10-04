package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.adapter.ServicesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.ServiceRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.ServiceViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ServicesListFragment extends Fragment {
    private AppViewModel appViewModel;
    private ServiceViewModel serviceViewModel;
    private List<Service> currentServices = new ArrayList<>();
    private ServicesAdapter servicesAdapter;
    private ServiceRepo servicesRepo;
    private int currentIndex = -1;

    public static ServicesListFragment newInstance() {
        return new ServicesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

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
                servicesRepo = serviceRepo;
            }
        });
        appViewModel.currentPathIndex.observe(getViewLifecycleOwner(), new Observer<Indexor>() {
            @Override
            public void onChanged(Indexor indexor) {
                if(null==indexor) return;
                currentIndex = indexor.index;
            }
        });
        appViewModel.currentPathMap.observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, PathData>>() {
            @Override
            public void onChanged(HashMap<Integer, PathData> integerPathDataHashMap) {
                if(currentIndex > 0){
                    try{
                    PathData currentPath = integerPathDataHashMap.get(currentIndex);
                    if(null==currentPath ||
                            null==currentPath.remoteAction ||
                            currentPath.remoteAction.trim().length() == 0
                    ) return;
                    if(currentPath.remoteAction.trim().equals(RA_SERVICES)){
                        servicesRepo.services();
                    }
                    if(currentPath.remoteAction.trim().equals(RA_SERVICES_BY_COUNTY)){
                        County thisCounty = (County) currentPath.data;
                        servicesRepo.serviceByCounty(thisCounty.name);
                    }
                    if(currentPath.remoteAction.trim().equals(RA_SERVICES_BY_FACILITY)){
                        Facility thisFacility = (Facility) currentPath.data;
                        servicesRepo.serviceByFacility(thisFacility.name);
                    }
                    serviceViewModel.filteredServices.observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
                        @Override
                        public void onChanged(List<Service> services) {
                            currentServices = services;
                            if(null==services || services.size() == 0) return;
                            servicesAdapter = new ServicesAdapter(
                                    appViewModel, services, currentIndex);
                            servicesView.setHasFixedSize(true);
                            servicesView.setLayoutManager(servicesLayoutManager);
                            servicesView.setAdapter(servicesAdapter);
                        }
                    });
                    }catch(ClassCastException ex){
                        Log.e(SYSTAG, ex.getLocalizedMessage());
                    }
                }
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