package com.foreverdevelopers.doctors_directory_kenya.ui;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;

import android.os.Bundle;
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
import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.CountyRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CountiesListFragment extends Fragment {

    private CountiesListViewModel viewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private CountyRepo countyRepo;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;
    private CountyViewModel countyViewModel;

    public static CountiesListFragment newInstance() {
        return new CountiesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CountiesListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        countyViewModel = new ViewModelProvider(requireActivity()).get(CountyViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_counties_list, container, false);
        RecyclerView.LayoutManager countiesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView countiesView = root.findViewById(R.id.rcv_counties);
        TextView title = root.findViewById(R.id.lbl_counties);

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
        appViewModel.activePathMap.observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, ActivePath>>() {
            @Override
            public void onChanged(HashMap<Integer, ActivePath> integerActivePathHashMap) {
                pathMap = integerActivePathHashMap;
            }
        });
        appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentIndex = integer;
                ActivePath activePath = pathMap.get(currentIndex);
                assert activePath != null;
                if(activePath.baseItem instanceof String){
                    String titleStr = activePath.remoteAction.trim().equals(RA_COUNTIES) ?
                            "Counties" :
                            ( activePath.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY) ?
                                    "County with Facility " + activePath.baseItem :
                                    (activePath.remoteAction.trim().equals(RA_COUNTIES_BY_SERVICE) ?
                                            "Counties with Service " + activePath.baseItem: "Counties"));
                    title.setText(titleStr);
                    if(activePath.remoteAction.trim().equals(RA_COUNTIES)) countyRepo.counties();
                    if(activePath.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY)) countyRepo.countiesByFacility((String) activePath.baseItem);
                    if(activePath.remoteAction.trim().equals(RA_COUNTIES_BY_SERVICE)) countyRepo.countiesByService((String) activePath.baseItem);
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
        countyViewModel.counties.observe(getViewLifecycleOwner(), new Observer<List<County>>() {
            @Override
            public void onChanged(List<County> counties) {
                viewModel.setCurrentCounties(counties);
            }
        });
        viewModel.currentCounties.observe(getViewLifecycleOwner(), new Observer<List<County>>() {
            @Override
            public void onChanged(List<County> counties) {
                if(null==counties || counties.size() == 0) return;
                RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> countiesAdapter = new CountiesAdapter(
                        appViewModel, counties, currentIndex, appNavController, pathMap);
                countiesView.setHasFixedSize(true);
                countiesView.setLayoutManager(countiesLayoutManager);
                countiesView.setAdapter(countiesAdapter);
            }
        });

        return root;
    }

}