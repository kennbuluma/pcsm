package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class FacilitiesListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> _facilities = new MutableLiveData<>();
    public LiveData<ArrayList<String>> facilities = _facilities;

    public void setFacilities(ArrayList<String> facilities){
        _facilities.postValue(facilities);
    }
}