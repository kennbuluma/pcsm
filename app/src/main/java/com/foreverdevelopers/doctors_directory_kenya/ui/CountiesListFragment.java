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

import java.util.ArrayList;
import java.util.HashMap;

public class CountiesListFragment extends Fragment {

    private CountiesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private Requests mainRequests;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;

    public static CountiesListFragment newInstance() {
        return new CountiesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(CountiesListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_counties_list, container, false);
        RecyclerView.LayoutManager countiesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView countiesView = root.findViewById(R.id.rcv_counties);
        TextView title = root.findViewById(R.id.lbl_counties);

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
                    if(activePath.remoteAction.trim().equals(RA_COUNTIES)) mainRequests.countiesAll();
                    if(activePath.remoteAction.trim().equals(RA_COUNTIES_BY_FACILITY)) mainRequests.countiesByFacility((String) activePath.baseItem);
                    if(activePath.remoteAction.trim().equals(RA_COUNTIES_BY_SERVICE)) mainRequests.countiesByService((String) activePath.baseItem);
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
        appViewModel.counties.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(null==strings || strings.size() == 0) return;
                RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> countiesAdapter = new CountiesAdapter(appViewModel, strings, currentIndex, appNavController, pathMap);
                countiesView.setHasFixedSize(true);
                countiesView.setLayoutManager(countiesLayoutManager);
                countiesView.setAdapter(countiesAdapter);
            }
        });

        return root;
    }

}