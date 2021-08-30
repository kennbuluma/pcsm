package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_SERVICE;
import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_FACILITY;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        loadComponents(root);
        return root;
    }

    private void loadComponents(View root){
        ConstraintLayout doctorsList = root.findViewById(R.id.cl_doctors_list),
        homeView = root.findViewById(R.id.cl_home_components);
        doctorsList.setVisibility(View.GONE);
        MaterialButton btnDoctors = root.findViewById(R.id.btn_main_doctors),
            btnCounties = root.findViewById(R.id.btn_main_counties),
            btnServices = root.findViewById(R.id.btn_main_services),
            btnFacilities = root.findViewById(R.id.btn_main_facilities);
        TextInputLayout etlSearch = root.findViewById(R.id.etl_main_search);
        TextInputEditText etSearch = root.findViewById(R.id.et_main_search);


        mViewModel.showHome.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    homeView.setVisibility(View.VISIBLE);
                    doctorsList.setVisibility(View.GONE);
                }else{
                    homeView.setVisibility(View.GONE);
                    doctorsList.setVisibility(View.VISIBLE);
                }
            }
        });
        btnCounties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Integer, ActivePath> pathMapper = new HashMap<>();
                pathMapper.put(0, new ActivePath("counties", RA_COUNTIES));
                pathMapper.put(1, new ActivePath("facilities", RA_FACILITIES_BY_COUNTY));
                pathMapper.put(2, new ActivePath("services", RA_SERVICES_BY_FACILITY));
                pathMapper.put(3, new ActivePath("doctors", RA_DOCTORS_BY_SERVICE));
                pathMapper.put(4, new ActivePath("doctor", ""));
                appViewModel.setActivePathMap(pathMapper);
                appViewModel.setCurrentIndex(0);
                appNavController.navigate(R.id.nav_counties);
            }
        });
        btnDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Integer, ActivePath> pathMapper = new HashMap<>();
                pathMapper = new HashMap<>();
                pathMapper.put(0, new ActivePath("doctors", RA_DOCTORS));
                pathMapper.put(1, new ActivePath("doctor", ""));
                appViewModel.setActivePathMap(pathMapper);
                appViewModel.setCurrentIndex(0);
                appNavController.navigate(R.id.nav_doctors);
            }
        });
        btnFacilities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Integer, ActivePath> pathMapper = new HashMap<>();
                pathMapper.put(0, new ActivePath("facilities", RA_FACILITIES));
                pathMapper.put(1, new ActivePath("counties", RA_COUNTIES_BY_FACILITY));
                pathMapper.put(2, new ActivePath("services", RA_SERVICES_BY_COUNTY));
                pathMapper.put(3, new ActivePath("doctors", RA_DOCTORS_BY_SERVICE));
                pathMapper.put(4, new ActivePath("doctor", ""));
                appViewModel.setActivePathMap(pathMapper);
                appViewModel.setCurrentIndex(0);
                appNavController.navigate(R.id.nav_facilities);
            }
        });
        btnServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<Integer, ActivePath> pathMapper = new HashMap<>();
                pathMapper.put(0, new ActivePath("services", RA_SERVICES));
                pathMapper.put(1, new ActivePath("counties", RA_COUNTIES_BY_SERVICE));
                pathMapper.put(2, new ActivePath("facilities", RA_FACILITIES_BY_COUNTY));
                pathMapper.put(3, new ActivePath("doctors", RA_DOCTORS_BY_FACILITY));
                pathMapper.put(4, new ActivePath("doctor", ""));
                appViewModel.setActivePathMap(pathMapper);
                appViewModel.setCurrentIndex(0);
                appNavController.navigate(R.id.nav_services);
            }
        });

        etlSearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String searchVal = etSearch.getText().toString().trim().toLowerCase(Locale.ROOT);
                    if(null==searchVal || searchVal.trim().length() == 0){
                        Toast.makeText(requireActivity(), "Blank search value!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(requireActivity(), "Searching for "+searchVal, Toast.LENGTH_SHORT).show();
                }catch(NullPointerException ex){
                    Toast.makeText(requireActivity(), "Blank search value!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}