package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foreverdevelopers.m_daktari.data.entity.Doctor;

import java.util.ArrayList;

public class DoctorsListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Doctor>> _doctors = new MutableLiveData<>();
    public LiveData<ArrayList<Doctor>> doctors = _doctors;

    public void setDoctors(ArrayList<Doctor> doctors){
        _doctors.postValue(doctors);
    }
}