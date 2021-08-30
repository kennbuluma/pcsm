package com.foreverdevelopers.m_daktari.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<Boolean> _showHome = new MutableLiveData<>();
    public LiveData<Boolean> showHome = this._showHome;

    public void setShowHome(Boolean show){
        this._showHome.postValue(show);
    }
}