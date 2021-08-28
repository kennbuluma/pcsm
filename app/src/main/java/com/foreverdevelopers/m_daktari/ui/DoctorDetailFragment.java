package com.foreverdevelopers.m_daktari.ui;

import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_FACILITY;
import static com.foreverdevelopers.m_daktari.util.Common.RA_DOCTORS_BY_SERVICE;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.remote.Requests;

import java.util.HashMap;

public class DoctorDetailFragment extends Fragment {

    private DoctorDetailViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController;
    private Integer currentIndex;
    private HashMap<Integer, ActivePath> pathMap;
    private Requests mainRequests;

    public static DoctorDetailFragment newInstance() {
        return new DoctorDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DoctorDetailViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        View root = inflater.inflate(R.layout.fragment_doctor_detail, container, false);
        TextView title = root.findViewById(R.id.txt_doc_detail_title),
                name = root.findViewById(R.id.txt_doc_detail_highlight_name),
                county = root.findViewById(R.id.txt_doc_detail_highlight_county),
                facility = root.findViewById(R.id.txt_doc_detail_highlight_facility),
                phone = root.findViewById(R.id.txt_doc_detail_contact_phone),
                email = root.findViewById(R.id.txt_doc_detail_contact_email);
        title.setText("Doctor's Detail");
        appViewModel.remoteRequests.observe(getViewLifecycleOwner(), new Observer<Requests>() {
            @Override
            public void onChanged(Requests requests) {
                mainRequests = requests;
                mainRequests.setDoctorViewModel(mViewModel);
            }
        });
        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        appViewModel.currentIndex.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                currentIndex = integer;
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
                //mainRequests.doctorDetails(activePath.baseItem);
            }
        });
        mViewModel.doctor.observe(getViewLifecycleOwner(), new Observer<Doctor>() {
            @Override
            public void onChanged(Doctor doctor) {
                name.setText(doctor.name);
                county.setText(doctor.county);
                facility.setText(doctor.facility);
                phone.setText(doctor.phone);
                email.setText(doctor.email);
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