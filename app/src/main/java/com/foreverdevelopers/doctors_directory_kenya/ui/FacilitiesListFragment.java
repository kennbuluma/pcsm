package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.foreverdevelopers.doctors_directory_kenya.adapter.FacilitiesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.FacilityRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.FacilityViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FacilitiesListFragment extends Fragment {

    private FacilitiesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private FacilityViewModel facilityViewModel;
    private NavController appNavController = null;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;
    private List<Facility> currentFacilities= new ArrayList<>();
    private FacilitiesAdapter facilitiesAdapter;
    private FacilityRepo facilitiesRepo;

    public static FacilitiesListFragment newInstance() {
        return new FacilitiesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FacilitiesListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        facilityViewModel = new ViewModelProvider(requireActivity()).get(FacilityViewModel.class);

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

        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        appViewModel.facilityRepo.observe(getViewLifecycleOwner(), new Observer<FacilityRepo>() {
            @Override
            public void onChanged(FacilityRepo facilityRepo) {
                facilitiesRepo = facilityRepo;
            }
        });
        appViewModel.activePathMap.observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, ActivePath>>() {
            @Override
            public void onChanged(HashMap<Integer, ActivePath> integerActivePathHashMap) {
                pathMap = integerActivePathHashMap;
                appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        currentIndex = integer;
                        ActivePath activePath = pathMap.get(currentIndex);
                        if(activePath.currentPath.remoteAction.trim().equals(RA_FACILITIES)){
                            facilitiesRepo.facilities();
                        }
                        if(activePath.currentPath.remoteAction.trim().equals(RA_FACILITIES_BY_COUNTY)){
                            County thisCounty = (County) activePath.currentPath.data;
                            facilitiesRepo.facilityByCounty(thisCounty.name);
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
            }
        });
        facilityViewModel.filteredFacilities.observe(getViewLifecycleOwner(), new Observer<List<Facility>>() {
            @Override
            public void onChanged(List<Facility> facilities) {
                currentFacilities = facilities;
                if(null==facilities || facilities.size() == 0) return;
                facilitiesAdapter = new FacilitiesAdapter(
                        appViewModel, facilities, currentIndex, appNavController, pathMap);
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