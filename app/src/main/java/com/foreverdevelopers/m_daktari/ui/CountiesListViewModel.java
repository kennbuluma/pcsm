package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CountiesListViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> _counties = new MutableLiveData<>();
    public LiveData<ArrayList<String>> counties = _counties;

    public void setCounties(ArrayList<String> counties){
        _counties.postValue(counties);
    }
}