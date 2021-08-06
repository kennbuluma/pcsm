package com.csm.projectcsm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

public class AppViewModel extends ViewModel {
    private final MutableLiveData<NavController> _navController = new MutableLiveData<NavController>();
    public void setNavController(NavController controller){
        this._navController.postValue(controller);
    }
    public LiveData<NavController> navController = _navController;
}
