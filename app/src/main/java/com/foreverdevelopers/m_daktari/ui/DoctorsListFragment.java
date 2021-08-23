package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_SERVICE;

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
import com.foreverdevelopers.m_daktari.adapter.CountiesAdapter;
import com.foreverdevelopers.m_daktari.adapter.DoctorsAdapter;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DoctorsListFragment extends Fragment {

    private DoctorsListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private HashMap<Integer, ActivePath> pathMapper = null;
    private String activeBaseItem = null;
    private Requests mainRequests;
    private Integer currentIndex = -1;

    public static DoctorsListFragment newInstance() {
        return new DoctorsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DoctorsListViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doctors_list, container, false);
        RecyclerView.LayoutManager doctorsLayoutManager = new LinearLayoutManager(root.getContext());
        RecyclerView doctorsView = root.findViewById(R.id.rcv_doctors);
        TextView title = root.findViewById(R.id.lbl_doc_list);
        appViewModel.remoteRequests.observe(getViewLifecycleOwner(), new Observer<Requests>() {
            @Override
            public void onChanged(Requests requests) {
                mainRequests = requests;
                mainRequests.setDoctorsViewModel(mViewModel);
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
                    if (pathItem.getValue().path.trim().toLowerCase(Locale.ROOT).equals("doctors")) {
                        switch (pathItem.getValue().remoteAction.trim().toLowerCase(Locale.ROOT)) {
                            case RA_DOCTORS: {
                                title.setText("Doctors");
                                currentIndex = pathItem.getKey();
                                 mainRequests.doctorsAll();
                                 appViewModel.setCurrentPath(pathItem.getValue());
                                break;
                            }
                            case RA_DOCTORS_BY_FACILITY: {
                                title.setText("Doctors in Facility " + activeBaseItem);
                                currentIndex = pathItem.getKey();
                                mainRequests.doctorsByFacility(activeBaseItem);
                                appViewModel.setCurrentPath(pathItem.getValue());
                                break;
                            }
                            case RA_DOCTORS_BY_SERVICE: {
                                title.setText("Doctors in Service " + activeBaseItem);
                                currentIndex = pathItem.getKey();
                                mainRequests.doctorsByService(activeBaseItem);
                                appViewModel.setCurrentPath(pathItem.getValue());
                                break;
                            }
                        }
                        ;
                        break;
                    }
                }
            }
        });
        mViewModel.doctors.observe(getViewLifecycleOwner(), new Observer<ArrayList<Doctor>>() {
            @Override
            public void onChanged(ArrayList<Doctor> doctors) {
                if(null==doctors || doctors.size() == 0) return;
                RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> doctorsAdapter = new DoctorsAdapter(doctors, appViewModel, currentIndex);
                doctorsView.setHasFixedSize(true);
                doctorsView.setLayoutManager(doctorsLayoutManager);
                doctorsView.setAdapter(doctorsAdapter);
            }
        });
        return root;
    }



}