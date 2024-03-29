package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTOR_DETAILS;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_SERVICES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.adapter.DoctorsArrayAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.FacilityViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.ServiceViewModel;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private AppViewModel appViewModel;
    private CountyViewModel countyViewModel;
    private FacilityViewModel facilityViewModel;
    private ServiceViewModel serviceViewModel;
    private DoctorViewModel doctorViewModel;
    private ArrayAdapter<Doctor> doctorAdapter;
    private AutoCompleteTextView atvSearch;
    private List<Doctor> doctors = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private NavController navController;
    private Boolean c, s, f, d = false;
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
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        loadComponents(root);
        return root;
    }

    private void loadComponents(View root){
        ProgressBar progressBar = root.findViewById(R.id.prg_home);
        CardView btnCounties = root.findViewById(R.id.btn_main_counties),
                btnServices = root.findViewById(R.id.btn_main_services),
                btnFacilities = root.findViewById(R.id.btn_main_facilities),
                btnDashboard = root.findViewById(R.id.crd_home_dasher);
        MaterialButton btnDoctors = root.findViewById(R.id.btn_home_doctors);
        FloatingActionButton btnHeart = root.findViewById(R.id.fab_home_heart),
                btnLungs = root.findViewById(R.id.fab_home_lungs),
                btnEye = root.findViewById(R.id.fab_home_eye),
                btnTooth = root.findViewById(R.id.fab_home_tooth);
        atvSearch = root.findViewById(R.id.atv_main_search);
        appViewModel.navController.observe(getViewLifecycleOwner(), controller -> navController = controller);
        appViewModel.showProgress.observe(getViewLifecycleOwner(), aBoolean -> {
            progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            progressBar.setIndeterminate(aBoolean);
        });
        sharedPreferences = requireContext().getSharedPreferences("router", Context.MODE_PRIVATE);

        btnDashboard.setOnClickListener(view -> new MaterialAlertDialogBuilder(requireContext(),R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setMessage("Covid 19 Information store")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("More info", (dialogInterface, i) -> Toast.makeText(getContext(), "More information loaded", Toast.LENGTH_LONG).show())
                .show());

        countyViewModel.counties.observe(getViewLifecycleOwner(), dbCounties -> {
            c = true;
            boolean all = null != f && null != d && null != s && f && d && s;
            if(null!= appViewModel) appViewModel.setShowProgress(all);
            btnCounties.setOnClickListener(view -> {
                countyViewModel.setFilteredCounties(dbCounties);
                setPrefData(R.id.nav_counties, RA_COUNTIES, "");
            });
        });
        facilityViewModel.facilities.observe(getViewLifecycleOwner(), dbFacilities -> {
            f = true;
            boolean all = null != c && null != d && null != s && c && d && s;
            if(null!= appViewModel) appViewModel.setShowProgress(all);
            btnFacilities.setOnClickListener(view -> {
                facilityViewModel.setFilteredFacilities(dbFacilities);
                setPrefData(R.id.nav_facilities, RA_FACILITIES, "");
            });
        });
        serviceViewModel.services.observe(getViewLifecycleOwner(), dbServices -> {
            s = true;
            boolean all = null != c && null != d && null != f && c && f && d;
            if(null!= appViewModel) appViewModel.setShowProgress(all);
            btnServices.setOnClickListener(view -> {
                serviceViewModel.setFilteredServices(dbServices);
                setPrefData(R.id.nav_services, RA_SERVICES, "");
            });
        });
        doctorViewModel.doctors.observe(getViewLifecycleOwner(), dbDoctors -> {
            d = true;
            boolean all = null != c && null != f && null != s && c && f && s;
            if(null!= appViewModel) appViewModel.setShowProgress(all);
            doctors = dbDoctors;
            btnDoctors.setOnClickListener(view -> {
                doctorViewModel.setFilteredDoctors(dbDoctors);
                setPrefData(R.id.nav_doctors, RA_DOCTORS, "");
            });
            btnHeart.setOnClickListener(view -> {
                doctorViewModel.setFilteredDoctors(dbDoctors);
                setPrefData(R.id.nav_doctors, RA_DOCTORS, "");
            });
            btnLungs.setOnClickListener(view -> {
                doctorViewModel.setFilteredDoctors(dbDoctors);
                setPrefData(R.id.nav_doctors, RA_DOCTORS, "");
            });
            btnTooth.setOnClickListener(view -> {
                doctorViewModel.setFilteredDoctors(dbDoctors);
                setPrefData(R.id.nav_doctors, RA_DOCTORS, "");
            });
            btnEye.setOnClickListener(view -> {
                doctorViewModel.setFilteredDoctors(dbDoctors);
                setPrefData(R.id.nav_doctors, RA_DOCTORS, "");
            });
            doctorAdapter = new DoctorsArrayAdapter(requireContext(), R.layout.list_item_name, doctors);
            atvSearch.setAdapter(doctorAdapter);
            atvSearch.setOnItemClickListener((adapterView, view, i, l) -> {
                Doctor doctor = (Doctor) adapterView.getAdapter().getItem(i);
                doctorViewModel.setDoctor(doctor);
                setPrefData(R.id.nav_doctor_details, RA_DOCTOR_DETAILS, Converter.objectToString(doctor));
            });
        });
    }

    private void setPrefData(int nextRoute, String nextAction, String nextData){
        SharedPreferences.Editor sharedEditor = sharedPreferences.edit();
        sharedEditor.putInt("nextRoute", nextRoute);
        sharedEditor.putString("nextAction",nextAction);
        sharedEditor.putString("nextData", nextData);
        sharedEditor.putInt("prevRoute", R.id.nav_home);
        sharedEditor.putString("prevAction","");
        sharedEditor.putString("prevData", "");
        if(!sharedEditor.commit() || null==navController) return;
        navController.navigate(nextRoute < 0 ? R.id.nav_home : nextRoute);
    }
}