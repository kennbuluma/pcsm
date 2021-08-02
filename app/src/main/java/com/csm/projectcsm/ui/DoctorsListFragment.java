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

public class DoctorsListFragment extends Fragment {

    private DoctorsListViewModel mViewModel;

    public static DoctorsListFragment newInstance() {
        return new DoctorsListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(DoctorsListViewModel.class);
        return inflater.inflate(R.layout.fragment_doctors_list, container, false);
    }

}