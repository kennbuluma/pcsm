package com.csm.projectcsm.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.csm.projectcsm.AppViewModel;
import com.csm.projectcsm.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private AppViewModel appViewModel;
    private NavController appNavController;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        appViewModel.navController.observe(getViewLifecycleOwner(), new Observer<NavController>() {
            @Override
            public void onChanged(NavController navController) {
                appNavController = navController;
            }
        });
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        loadComponents(root);
        return root;
    }

    private void loadComponents(View root){
        MaterialButton btnDoctors = root.findViewById(R.id.btn_main_doctors),
            btnCounties = root.findViewById(R.id.btn_main_counties),
            btnServices = root.findViewById(R.id.btn_main_services),
            btnFacilities = root.findViewById(R.id.btn_main_facilities);
        TextInputLayout etlSearch = root.findViewById(R.id.etl_main_search);
        TextInputEditText etSearch = root.findViewById(R.id.et_main_search);

        btnCounties.setOnClickListener(homeClicks);
        btnDoctors.setOnClickListener(homeClicks);
        btnFacilities.setOnClickListener(homeClicks);
        btnServices.setOnClickListener(homeClicks);

        etlSearch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String searchVal = etSearch.getText().toString().trim().toLowerCase(Locale.ROOT);
                    if(null==searchVal || searchVal.trim().length() == 0){
                        Toast.makeText(requireActivity(), "Blank search value!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(requireActivity(), "Searching for "+searchVal, Toast.LENGTH_SHORT).show();
                }catch(NullPointerException ex){
                    Toast.makeText(requireActivity(), "Blank search value!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private final View.OnClickListener homeClicks = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(null==appNavController) return;
            switch(v.getId()){
                case R.id.btn_main_counties:{
                    appNavController.navigate(R.id.nav_counties);
                    return;
                }
                case R.id.btn_main_doctors:{
                    appNavController.navigate(R.id.nav_doctors);
                    return;
                }
                case R.id.btn_main_facilities:{
                    appNavController.navigate(R.id.nav_facilities);
                    return;
                }
                case R.id.btn_main_services: appNavController.navigate(R.id.nav_services);
            }
        }
    };
}