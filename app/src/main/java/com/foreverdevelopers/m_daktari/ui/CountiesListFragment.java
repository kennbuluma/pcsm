package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.os.Bundle;
import android.util.Log;
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
import com.foreverdevelopers.m_daktari.adapter.CountiesAdapter;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CountiesListFragment extends Fragment {

    private CountiesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private String activeBaseItem = null;
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
        View root = inflater.inflate(R.layout.fragment_counties_list, container, false);
        RecyclerView.LayoutManager countiesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView countiesView = root.findViewById(R.id.rcv_counties);
        TextView title = root.findViewById(R.id.lbl_counties);
        appViewModel.remoteRequests.observe(getViewLifecycleOwner(), new Observer<Requests>() {
            @Override
            public void onChanged(Requests requests) {
                mainRequests = requests;
                mainRequests.setCountiesViewModel(mViewModel);
            }
        });
        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        appViewModel.activeBaseItem.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                activeBaseItem = s;
            }
        });
        appViewModel.currentPath.observe(getViewLifecycleOwner(), new Observer<ActivePath>() {
            @Override
            public void onChanged(ActivePath activePath) {
                switch (activePath.remoteAction.trim().toLowerCase(Locale.ROOT)) {
                    case RA_COUNTIES: {
                        title.setText("Counties");
                        mainRequests.countiesAll();
                        Log.w(SYSTAG, "All Counties");
                        break;
                    }
                    case RA_COUNTIES_BY_FACILITY: {
                        title.setText("County with Facility " + activeBaseItem);
                        mainRequests.countiesByFacility(activeBaseItem);
                        Log.w(SYSTAG, "Counties By Facility");
                        break;
                    }
                    case RA_COUNTIES_BY_SERVICE: {
                        title.setText("Counties with Service " + activeBaseItem);
                        mainRequests.countiesByService(activeBaseItem);
                        Log.w(SYSTAG, "Counties by Service");
                        break;
                    }
                }
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
            }
        });
        mViewModel.counties.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
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