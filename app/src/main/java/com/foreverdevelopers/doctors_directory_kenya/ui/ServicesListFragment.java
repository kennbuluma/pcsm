package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_FACILITY;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.foreverdevelopers.doctors_directory_kenya.adapter.ServicesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
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

public class ServicesListFragment extends Fragment {

    private ServicesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private ServiceViewModel serviceViewModel;
    private NavController appNavController = null;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;
    private List<Service> currentServices = new ArrayList<>();
    private ServicesAdapter servicesAdapter;
    private ServiceRepo servicesRepo;

    public static ServicesListFragment newInstance() {
        return new ServicesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ServicesListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);

        final View root  = inflater.inflate(R.layout.fragment_services, container, false);
        RecyclerView.LayoutManager servicesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView servicesView = root.findViewById(R.id.rcv_services);
        TextView title = root.findViewById(R.id.lbl_service_list);
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

        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        appViewModel.activePathMap.observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, ActivePath>>() {
            @Override
            public void onChanged(HashMap<Integer, ActivePath> integerActivePathHashMap) {
                pathMap = integerActivePathHashMap;
            }
        });
        appViewModel.serviceRepo.observe(getViewLifecycleOwner(), new Observer<ServiceRepo>() {
            @Override
            public void onChanged(ServiceRepo serviceRepo) {
                servicesRepo = serviceRepo;
            }
        });
        appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentIndex = integer;
                ActivePath activePath = pathMap.get(currentIndex);
                if(activePath.currentPath.remoteAction.trim().equals(RA_SERVICES)){
                    servicesRepo.services();
                }
                if(activePath.currentPath.remoteAction.trim().equals(RA_SERVICES_BY_COUNTY)){
                    County thisCounty = (County) activePath.currentPath.data;
                    servicesRepo.serviceByCounty(thisCounty.name);
                }
                if(activePath.currentPath.remoteAction.trim().equals(RA_SERVICES_BY_FACILITY)){
                    Facility thisFacility = (Facility) activePath.currentPath.data;
                    servicesRepo.serviceByFacility(thisFacility.name);
                }
                root.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
                            appViewModel.setCurrentIndex((currentIndex == 0) ? 0 : currentIndex-1);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        serviceViewModel.filteredServices.observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                currentServices = services;
                if(null==services || services.size() == 0) return;
                servicesAdapter = new ServicesAdapter(
                        appViewModel, services, currentIndex, appNavController, pathMap);
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