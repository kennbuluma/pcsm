package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES_BY_SERVICE;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.adapter.CountiesAdapter;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

public class CountiesListFragment extends Fragment {

    private CountiesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private HashMap<Integer, ActivePath> pathMapper = null;
    private String activeBaseItem = null;
    private Requests mainRequests;

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
        appViewModel.activePathMap.observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, ActivePath>>() {
            @Override
            public void onChanged(HashMap<Integer, ActivePath> integerActivePathHashMap) {
                pathMapper = integerActivePathHashMap;
                for (Map.Entry<Integer, ActivePath> pathItem : pathMapper.entrySet()) {
                    if (pathItem.getValue().path.trim().toLowerCase(Locale.ROOT).equals("counties")) {
                        switch (pathItem.getValue().remoteAction.trim().toLowerCase(Locale.ROOT)) {
                            case RA_COUNTIES: {
                                title.setText("Counties");
                                mainRequests.countiesAll();
                                break;
                            }
                            case RA_COUNTIES_BY_FACILITY: {
                                title.setText("County with Facility " + activeBaseItem);
                                mainRequests.countiesByFacility(activeBaseItem);
                                break;
                            }
                            case RA_COUNTIES_BY_SERVICE: {
                                title.setText("Counties with Service " + activeBaseItem);
                                mainRequests.countiesByService(activeBaseItem);
                                break;
                            }
                        }
                        ;
                        break;
                    }
                }
            }
        });
        mViewModel.counties.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(null==strings || strings.size() == 0) return;
                RecyclerView.Adapter<CountiesAdapter.CountyViewHolder> countiesAdapter = new CountiesAdapter(strings);
                countiesView.setHasFixedSize(true);
                countiesView.setLayoutManager(countiesLayoutManager);
                countiesView.setAdapter(countiesAdapter);
            }
        });
        return root;
    }

}