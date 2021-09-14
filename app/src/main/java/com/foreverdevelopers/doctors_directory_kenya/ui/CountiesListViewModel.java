package com.foreverdevelopers.doctors_directory_kenya.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;

import java.util.List;

public class CountiesListViewModel extends ViewModel {
    private final MutableLiveData<List<County>> _currentCounties = new MutableLiveData<>();
    public LiveData<List<County>> currentCounties = this._currentCounties;

    public void setCurrentCounties(List<County> counties){ this._currentCounties.postValue(counties);}
}