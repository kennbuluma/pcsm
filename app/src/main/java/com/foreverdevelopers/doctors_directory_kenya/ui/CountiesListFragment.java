package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;

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
import com.foreverdevelopers.doctors_directory_kenya.adapter.CountiesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.CountyRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CountiesListFragment extends Fragment {

    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private CountyRepo countyRepo;
    private PathData currentPath;
    private CountyViewModel countyViewModel;
    private List<County> currentCounties = new ArrayList<>();
    private CountiesAdapter countiesAdapter;
    private Boolean isReturning = false;

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
        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        appViewModel.previousPath.observe(getViewLifecycleOwner(), new Observer<PathData>() {
            @Override
            public void onChanged(PathData pathData) {
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
                currentPath = pathData;
                if(null == pathData.remoteAction ||
                        pathData.remoteAction.trim().equals(RA_COUNTIES)
                ) countyRepo.counties();
                if(pathData.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY)) {
                    Facility thisFacility =  null==pathData.data ? null : (Facility) pathData.data;
                    if(null!=thisFacility) countyRepo.countiesByFacility(thisFacility.name);
                }
                if(pathData.remoteAction.trim().equals(RA_COUNTIES_BY_SERVICE)){
                    Service thisService = null==pathData.data ? null : (Service) pathData.data;
                    if(null!=thisService) countyRepo.countiesByService(thisService.name);
                }
                countyViewModel.filteredCounties.observe(getViewLifecycleOwner(), new Observer<List<County>>() {
                    @Override
                    public void onChanged(List<County> counties) {
                        currentCounties = counties;
                        if(null==counties || counties.size() == 0) return;
                        countiesAdapter = new CountiesAdapter(
                                appViewModel, counties, currentPath, appNavController);
                        countiesView.setHasFixedSize(true);
                        countiesView.setLayoutManager(countiesLayoutManager);
                        countiesView.setAdapter(countiesAdapter);
                    }
                });
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