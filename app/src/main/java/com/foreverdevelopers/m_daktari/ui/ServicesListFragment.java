package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_COUNTY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_SERVICES_BY_FACILITY;

import android.os.Bundle;
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
import com.foreverdevelopers.m_daktari.adapter.ServicesAdapter;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class ServicesListFragment extends Fragment {

    private ServicesListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private String activeBaseItem = null;
    private Requests mainRequests;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;

    public static ServicesListFragment newInstance() {
        return new ServicesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ServicesListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        View root  = inflater.inflate(R.layout.fragment_services, container, false);
        RecyclerView.LayoutManager servicesLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView servicesView = root.findViewById(R.id.rcv_services);
        TextView title = root.findViewById(R.id.lbl_service_list);
        appViewModel.remoteRequests.observe(getViewLifecycleOwner(), new Observer<Requests>() {
            @Override
            public void onChanged(Requests requests) {
                mainRequests = requests;
                mainRequests.setServicesViewModel(mViewModel);
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
                pathMap = integerActivePathHashMap;
            }
        });
        appViewModel.currentPath.observe(getViewLifecycleOwner(), new Observer<ActivePath>() {
            @Override
            public void onChanged(ActivePath activePath) {
                if(activePath.remoteAction.trim().equals(RA_SERVICES)){
                    title.setText("Services");
                    mainRequests.servicesAll();
                }
                if(activePath.remoteAction.trim().equals(RA_SERVICES_BY_COUNTY)){
                    title.setText("Services in County " + activeBaseItem);
                    mainRequests.servicesByCounty(activeBaseItem);
                }
                if(activePath.remoteAction.trim().equals(RA_SERVICES_BY_FACILITY)){
                    title.setText("Services in Facility " + activeBaseItem);
                    mainRequests.servicesByFacility(activeBaseItem);
                }
            }
        });
        appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentIndex = integer;
            }
        });
        mViewModel.services.observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                if(null==strings || strings.size() == 0) return;
                RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> servicesAdapter = new ServicesAdapter(appViewModel, strings, currentIndex, appNavController, pathMap);
                servicesView.setHasFixedSize(true);
                servicesView.setLayoutManager(servicesLayoutManager);
                servicesView.setAdapter(servicesAdapter);
            }
        });

        return root;
    }
}