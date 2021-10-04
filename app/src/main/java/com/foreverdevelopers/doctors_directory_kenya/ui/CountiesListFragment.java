package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.adapter.CountiesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.CountyRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CountiesListFragment extends Fragment {

    private AppViewModel appViewModel;
    private CountyRepo countyRepo;
    private CountyViewModel countyViewModel;
    private List<County> currentCounties = new ArrayList<>();
    private CountiesAdapter countiesAdapter;
    private int currentIndex = -1;

    public static CountiesListFragment newInstance() {
        return new CountiesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        countyViewModel = new ViewModelProvider(requireActivity()).get(CountyViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_counties_list, container, false);
        RecyclerView.LayoutManager countiesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView countiesView = root.findViewById(R.id.rcv_counties);
        TextView title = root.findViewById(R.id.lbl_counties);
        TextInputEditText countySearch = root.findViewById(R.id.et_county_search);
        countySearch.addTextChangedListener(new TextWatcher() {
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

        appViewModel.countyRepo.observe(getViewLifecycleOwner(), new Observer<CountyRepo>() {
            @Override
            public void onChanged(CountyRepo repo) {
                countyRepo = repo;
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
                    PathData currentPath = integerPathDataHashMap.get(currentIndex);
                    if(null==currentPath ||
                            null==currentPath.remoteAction ||
                            currentPath.remoteAction.trim().length() == 0
                    ) return;
                    try{
                        if(null == currentPath.remoteAction ||
                                currentPath.remoteAction.trim().equals(RA_COUNTIES)
                        ) countyRepo.counties();
                        if(currentPath.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY)) {
                            Facility thisFacility =  null==currentPath.data ? null : (Facility) currentPath.data;
                            if(null!=thisFacility) countyRepo.countiesByFacility(thisFacility.name);
                        }
                        if(currentPath.remoteAction.trim().equals(RA_COUNTIES_BY_SERVICE)){
                            Service thisService = null==currentPath.data ? null : (Service) currentPath.data;
                            if(null!=thisService) countyRepo.countiesByService(thisService.name);
                        }
                    }catch(ClassCastException ex){
                        Log.e(SYSTAG, ex.getLocalizedMessage());
                    }

                    countyViewModel.filteredCounties.observe(getViewLifecycleOwner(), new Observer<List<County>>() {
                        @Override
                        public void onChanged(List<County> counties) {
                            currentCounties = counties;
                            if(null==counties || counties.size() == 0) return;
                            countiesAdapter = new CountiesAdapter(
                                    appViewModel, counties, currentIndex, integerPathDataHashMap);
                            countiesView.setHasFixedSize(true);
                            countiesView.setLayoutManager(countiesLayoutManager);
                            countiesView.setAdapter(countiesAdapter);
                        }
                    });
                }
            }
        });

        return root;
    }

    private void filter(CharSequence searchValue){
        ArrayList<County> counties = new ArrayList<>();
        for(County county : currentCounties){
            if(county.name.toLowerCase(Locale.ROOT).contains(searchValue.toString().toLowerCase(Locale.ROOT))) counties.add(county);
        }
        if(!counties.isEmpty()) countiesAdapter.filterCounties(counties);
    }
}