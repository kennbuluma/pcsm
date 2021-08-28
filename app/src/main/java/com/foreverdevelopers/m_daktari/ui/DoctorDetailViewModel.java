package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foreverdevelopers.m_daktari.data.entity.Doctor;

public class DoctorDetailViewModel extends ViewModel {
    private final MutableLiveData<Doctor> _doctor = new MutableLiveData<>();
    public LiveData<Doctor> doctor = _doctor;

    public void setDoctor(Doctor doctor){ this._doctor.postValue(doctor); }
}