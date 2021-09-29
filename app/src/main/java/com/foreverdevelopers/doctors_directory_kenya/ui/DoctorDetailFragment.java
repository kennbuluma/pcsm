package com.foreverdevelopers.doctors_directory_kenya.ui;

import android.content.Intent;
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

import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.foreverdevelopers.doctors_directory_kenya.util.Converter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DoctorDetailFragment extends Fragment {
    private DoctorViewModel doctorViewModel;
    private Doctor currentDoctor;

    public static DoctorDetailFragment newInstance() {
        return new DoctorDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        doctorViewModel = new ViewModelProvider(requireActivity()).get(DoctorViewModel.class);

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
        doctorViewModel.getDoctor.observe(getViewLifecycleOwner(), new Observer<Doctor>() {
            @Override
            public void onChanged(Doctor doctor) {
                if(null == doctor) return;
                currentDoctor = doctor;
                if(null!=name && null!=currentDoctor.name) name.setText(currentDoctor.name);
                if(null!=county && null!=currentDoctor.county) county.setText(currentDoctor.county);
                if(null!=facility && null!=currentDoctor.facility) facility.setText(currentDoctor.facility);
                if(null!=facility && null!=currentDoctor.facility) facility.setText(currentDoctor.facility);
                if(null!=phone && null!=currentDoctor.phone){
                    String phoneConv = Converter.phoneToString(currentDoctor.phone);
                    phone.setText(phoneConv);
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+phoneConv));
                            startActivity(callIntent);
                        }
                    });
                }
                if(null!=email && null!=currentDoctor.email){
                    email.setText(currentDoctor.email);
                    mail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //TODO: Add Email Procedure
                        }
                    });
                }

            }
        });
        title.setText("Doctor's Detail");
    }

}