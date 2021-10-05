package com.foreverdevelopers.doctors_directory_kenya.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DoctorDetailFragment extends Fragment {
    private String nextData;
    private DoctorViewModel doctorViewModel;

    public static DoctorDetailFragment newInstance() {
        return new DoctorDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        doctorViewModel = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("router", Context.MODE_PRIVATE);
        int nextRoute = sharedPreferences.getInt("nextRoute", -1);
        String nextAction = sharedPreferences.getString("nextAction", "");
        nextData = sharedPreferences.getString("nextData", "");
        int prevRoute = sharedPreferences.getInt("prevRoute", -1);
        String prevAction = sharedPreferences.getString("prevAction", "");
        String prevData = sharedPreferences.getString("prevData", "");
        final View root = inflater.inflate(R.layout.fragment_doctor_detail, container, false);
        loadUI(root);

        return root;
    }

    private void loadUI(View root){
        TextView title = root.findViewById(R.id.txt_doc_detail_title),
                name = root.findViewById(R.id.txt_doc_detail_highlight_name),
                county = root.findViewById(R.id.txt_doc_detail_highlight_county),
                facility = root.findViewById(R.id.txt_doc_detail_highlight_facility),
        phone = root.findViewById(R.id.txt_det_phone),
        email = root.findViewById(R.id.txt_det_email);
        FloatingActionButton call = root.findViewById(R.id.fab_docdet_call),
                mail = root.findViewById(R.id.fab_docdet_mail);
        title.setText("Doctor's Detail");

        doctorViewModel.getDoctor.observe(getViewLifecycleOwner(), new Observer<Doctor>() {
            @Override
            public void onChanged(Doctor doctori) {

                Doctor doctor = null!=doctori ? doctori : Converter.stringToDoctor(nextData);
                if(null ==  doctori) return;
                if(null != name) name.setText(doctor.name);
                if(null!=county && null!=doctor.county) county.setText(doctor.county);
                if(null!=facility && null!=doctor.facility) facility.setText(doctor.facility);
                if(null!=facility && null!=doctor.facility) facility.setText(doctor.facility);
                if(null!=phone && null!=doctor.phone){
                    String phoneConv = Converter.phoneToString(doctor.phone);
                    phone.setText(phoneConv);
                    call.setOnClickListener(view -> {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+phoneConv));
                        startActivity(callIntent);
                    });
                }
                if(null!=email && null!=doctor.email){
                    email.setText(doctor.email);
                    mail.setOnClickListener(view -> {
                        //TODO: Add Email Procedure
                    });
                }
            }
        });
    }

}