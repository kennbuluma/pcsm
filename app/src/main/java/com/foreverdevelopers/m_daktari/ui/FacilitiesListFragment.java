package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_FACILITIES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.os.Bundle;
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

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.adapter.FacilitiesAdapter;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.ArrayList;
import java.util.HashMap;

public class FacilitiesListFragment extends Fragment {

    private FacilitiesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private Requests mainRequests;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;

    public static FacilitiesListFragment newInstance() {
        return new FacilitiesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(FacilitiesListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_facilities_list, container, false);
        RecyclerView.LayoutManager facilitiesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView facilitiesView = root.findViewById(R.id.rcv_facilities);
        TextView title = root.findViewById(R.id.lbl_facility_list);

        appViewModel.remoteRequests.observe(getViewLifecycleOwner(), new Observer<Requests>() {
            @Override
            public void onChanged(Requests requests) {
                mainRequests = requests;
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
                appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        currentIndex = integer;
                        ActivePath activePath = pathMap.get(currentIndex);
                        if(activePath.remoteAction.trim().equals(RA_FACILITIES)){
                            title.setText("Facilities");
                            mainRequests.facilitiesAll();
                            Log.w(SYSTAG, "All Facilities");
                        }
                        if(activePath.remoteAction.trim().equals(RA_FACILITIES_BY_COUNTY)){
                            title.setText("Facilities in " + activePath.baseItem+ " County");
                            mainRequests.facilitiesByCounty((String)activePath.baseItem);
                            Log.w(SYSTAG, "Facilities By County");
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
        appViewModel.facilities.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(null==strings || strings.size() == 0) return;
                RecyclerView.Adapter<FacilitiesAdapter.FacilityViewHolder> facilitiesAdapter = new FacilitiesAdapter(appViewModel, strings, currentIndex, appNavController, pathMap);
                facilitiesView.setHasFixedSize(true);
                facilitiesView.setLayoutManager(facilitiesLayoutManager);
                facilitiesView.setAdapter(facilitiesAdapter);
            }
        });

        return root;
    }
}