package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_DOCTORS_BY_SERVICE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.foreverdevelopers.doctors_directory_kenya.adapter.DoctorsAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.DoctorRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DoctorsListFragment extends Fragment {

    private AppViewModel appViewModel;
    private DoctorViewModel doctorViewModel;
    private NavController appNavController = null;
    private List<Doctor> currentDoctors = new ArrayList<>();
    private DoctorsAdapter doctorsAdapter;
    private PathData previousPath;
    private DoctorRepo doctorsRepo;
    private Boolean isReturning = false;

    public static DoctorsListFragment newInstance() {
        return new DoctorsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        doctorViewModel = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_doctors_list, container, false);
        RecyclerView.LayoutManager doctorsLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView doctorsView = root.findViewById(R.id.rcv_doctors);
        TextInputEditText doctorSearch = root.findViewById(R.id.et_doctor_search);
        doctorSearch.addTextChangedListener(new TextWatcher() {
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
        appViewModel.doctorRepo.observe(getViewLifecycleOwner(), new Observer<DoctorRepo>() {
            @Override
            public void onChanged(DoctorRepo doctorRepo) {
                doctorsRepo = doctorRepo;
            }
        });
        appViewModel.previousPath.observe(getViewLifecycleOwner(), new Observer<PathData>() {
            @Override
            public void onChanged(PathData pathData) {
                previousPath = pathData;
                root.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
                            isReturning = true;
                            appViewModel.setCurrentPath(pathData);
                            appNavController.navigate(pathData.path);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
        appViewModel.currentPath.observe(getViewLifecycleOwner(), new Observer<PathData>() {
            @Override
            public void onChanged(PathData pathData) {
                if(isReturning) return;
                if(null == pathData.remoteAction ||
                        pathData.remoteAction.trim().equals(RA_DOCTORS)
                ) doctorsRepo.doctors();
                if(pathData.remoteAction.equals(RA_DOCTORS_BY_FACILITY)){
                    Facility thisFacility = (Facility) pathData.data;
                    doctorsRepo.doctorsByFacility(thisFacility.name);
                }
                if(pathData.remoteAction.equals(RA_DOCTORS_BY_SERVICE)){
                    Service thisService = (Service) pathData.data;
                    doctorsRepo.doctorsByService(thisService.name);
                }
                doctorViewModel.filteredDoctors.observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
                    @Override
                    public void onChanged(List<Doctor> doctors) {
                        currentDoctors = doctors;
                        if(null==doctors || doctors.size() == 0) return;
                        doctorsAdapter = new DoctorsAdapter(appViewModel, doctorViewModel, doctors,previousPath, appNavController);
                        doctorsView.setHasFixedSize(true);
                        doctorsView.setLayoutManager(doctorsLayoutManager);
                        doctorsView.setAdapter(doctorsAdapter);
                    }
                });
            }
        });
        return root;
    }

    private void filter(CharSequence searchValue){
        ArrayList<Doctor> doctors = new ArrayList<>();
        for(Doctor doctor : currentDoctors){
            if(doctor.name.toLowerCase(Locale.ROOT).contains(searchValue.toString().toLowerCase(Locale.ROOT))) doctors.add(doctor);
        }
        if(!doctors.isEmpty()) doctorsAdapter.filterDoctors(doctors);
    }

}