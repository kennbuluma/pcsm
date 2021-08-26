package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foreverdevelopers.m_daktari.data.entity.Doctor;

import java.util.ArrayList;

public class DoctorsListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Object>> _doctors = new MutableLiveData<>();
    public LiveData<ArrayList<Object>> doctors = _doctors;

    public void setDoctors(ArrayList<Object> doctors){
        _doctors.postValue(doctors);
    }
}