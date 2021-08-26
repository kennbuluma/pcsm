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
import com.foreverdevelopers.m_daktari.adapter.DoctorsAdapter;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class DoctorsListFragment extends Fragment {

    private DoctorsListViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController = null;
    private String activeBaseItem = null;
    private Requests mainRequests;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;

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
                pathMap = integerActivePathHashMap;
            }
        });
        appViewModel.currentPath.observe(getViewLifecycleOwner(), new Observer<ActivePath>() {
            @Override
            public void onChanged(ActivePath activePath) {
                switch (activePath.remoteAction.trim().toLowerCase(Locale.ROOT)) {
                    case RA_DOCTORS: {
                        title.setText("Doctors");
                        mainRequests.doctorsAll();
                        break;
                    }
                    case RA_DOCTORS_BY_FACILITY: {
                        title.setText("Doctors in Facility " + activeBaseItem);
                        mainRequests.doctorsByFacility(activeBaseItem);
                        break;
                    }
                    case RA_DOCTORS_BY_SERVICE: {
                        title.setText("Doctors in Service " + activeBaseItem);
                        mainRequests.doctorsByService(activeBaseItem);
                        break;
                    }
                }
            }
        });
        appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentIndex = integer;
            }
        });
        mViewModel.doctors.observe(getViewLifecycleOwner(), new Observer<ArrayList<Doctor>>() {
            @Override
            public void onChanged(ArrayList<Doctor> doctors) {
                if(null==doctors || doctors.size() == 0) return;
                RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> doctorsAdapter = new DoctorsAdapter(appViewModel, doctors, currentIndex, appNavController, pathMap);
                doctorsView.setHasFixedSize(true);
                doctorsView.setLayoutManager(doctorsLayoutManager);
                doctorsView.setAdapter(doctorsAdapter);
            }
        });

        return root;
    }



}