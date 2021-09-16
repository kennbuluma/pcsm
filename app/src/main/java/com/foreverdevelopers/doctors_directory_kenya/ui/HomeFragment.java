package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES_BY_FACILITY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.adapter.DoctorsArrayAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.FacilityViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.ServiceViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private AppViewModel appViewModel;
    private NavController appNavController;
    private CountyViewModel countyViewModel;
    private FacilityViewModel facilityViewModel;
    private ServiceViewModel serviceViewModel;
    private DoctorViewModel doctorViewModel;
    private ArrayAdapter<Doctor> doctorAdapter;
    private AutoCompleteTextView atvSearch;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        countyViewModel = new ViewModelProvider(requireActivity()).get(CountyViewModel.class);
        facilityViewModel = new ViewModelProvider(requireActivity()).get(FacilityViewModel.class);
        serviceViewModel = new ViewModelProvider(requireActivity()).get(ServiceViewModel.class);
        doctorViewModel = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);
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
        CardView btnDoctors = root.findViewById(R.id.btn_main_doctors),
            btnCounties = root.findViewById(R.id.btn_main_counties),
            btnServices = root.findViewById(R.id.btn_main_services),
            btnFacilities = root.findViewById(R.id.btn_main_facilities);
        TextInputLayout etlSearch = root.findViewById(R.id.etl_main_search);
        atvSearch = root.findViewById(R.id.atv_main_search);
        countyViewModel.counties.observe(getViewLifecycleOwner(), new Observer<List<County>>() {
            @Override
            public void onChanged(List<County> counties) {
                btnCounties.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int path = R.id.nav_counties;
                        countyViewModel.setFilteredCounties(counties);
                        appViewModel.setCurrentPath(new PathData(RA_COUNTIES, null, path));
                        appNavController.navigate(path);
                    }
                });
            }
        });
        facilityViewModel.facilities.observe(getViewLifecycleOwner(), new Observer<List<Facility>>() {
            @Override
            public void onChanged(List<Facility> facilities) {
                btnFacilities.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int path = R.id.nav_facilities;
                        facilityViewModel.setFilteredFacilities(facilities);
                        appViewModel.setCurrentPath(new PathData(RA_FACILITIES, null, path));
                        appNavController.navigate(path);
                    }
                });
            }
        });
        serviceViewModel.services.observe(getViewLifecycleOwner(), new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                btnServices.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int path = R.id.nav_services;
                        serviceViewModel.setFilteredServices(services);
                        appViewModel.setCurrentPath(new PathData(RA_SERVICES, null, path));
                        appNavController.navigate(path);
                    }
                });
            }
        });
        doctorViewModel.doctors.observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                doctorAdapter = new DoctorsArrayAdapter(requireContext(), R.layout.list_item_name, doctors);
                atvSearch.setAdapter(doctorAdapter);
                atvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Doctor doctor = (Doctor) adapterView.getAdapter().getItem(i);
                        doctorViewModel.setDoctor(doctor);
                        appNavController.navigate(R.id.nav_doctor_details);
                    }
                });
                btnDoctors.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int path = R.id.nav_doctors;
                        doctorViewModel.setFilteredDoctors(doctors);
                        appViewModel.setCurrentPath(new PathData(RA_DOCTORS, null, path));
                        appNavController.navigate(path);
                    }
                });
            }
        });
    }
}