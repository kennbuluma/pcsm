package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ServicesListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> _services = new MutableLiveData<>();
    public LiveData<ArrayList<String>> services = _services;

    public void setServices(ArrayList<String> services){
        _services.postValue(services);
    }
}