package com.csm.projectcsm.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csm.projectcsm.R;

public class DoctorDetailFragment extends Fragment {

    private DoctorDetailViewModel mViewModel;

    public static DoctorDetailFragment newInstance() {
        return new DoctorDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DoctorDetailViewModel.class);
        return inflater.inflate(R.layout.fragment_doctor_detail, container, false);
    }

}