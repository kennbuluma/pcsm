package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_SERVICE;

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
        appViewModel.activePathMap.observe(getViewLifecycleOwner(), new Observer<HashMap<Integer, ActivePath>>() {
            @Override
            public void onChanged(HashMap<Integer, ActivePath> integerActivePathHashMap) {
                pathMap = integerActivePathHashMap;
            }
        });
        appViewModel.currentPath.observe(getViewLifecycleOwner(), new Observer<ActivePath>() {
            @Override
            public void onChanged(ActivePath activePath) {
                if(activePath.remoteAction.trim().equals(RA_DOCTORS)){
                    title.setText("Doctors");
                    mainRequests.doctorsAll();
                }
                if(activePath.remoteAction.trim().equals(RA_DOCTORS_BY_FACILITY)){
                    title.setText("Doctors in Facility " + activePath.baseItem);
                    mainRequests.doctorsByFacility(activePath.baseItem);
                }
                if(activePath.remoteAction.trim().equals(RA_DOCTORS_BY_SERVICE)){
                    title.setText("Doctors in Service " + activePath.baseItem);
                    mainRequests.doctorsByService(activePath.baseItem);
                }
            }
        });
        appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentIndex = integer;
            }
        });
        mViewModel.doctors.observe(getViewLifecycleOwner(), new Observer<ArrayList<Object>>() {
            @Override
            public void onChanged(ArrayList<Object> doctors) {
                if(null==doctors || doctors.size() == 0) return;
                RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> doctorsAdapter = new DoctorsAdapter(appViewModel, doctors, currentIndex, appNavController, pathMap);
                doctorsView.setHasFixedSize(true);
                doctorsView.setLayoutManager(doctorsLayoutManager);
                doctorsView.setAdapter(doctorsAdapter);
            }
        });


        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    Integer mindex = (currentIndex == 0) ? 0 : currentIndex-1;
                    appViewModel.setCurrentIndex(mindex);
                    appViewModel.setCurrentPath(pathMap.get(mindex));
                    return true;
                }
                return false;
            }
        });

        return root;
    }



}