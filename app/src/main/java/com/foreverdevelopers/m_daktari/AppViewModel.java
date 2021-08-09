package com.foreverdevelopers.m_daktari;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.foreverdevelopers.m_daktari.data.HttpClient;
import com.foreverdevelopers.m_daktari.data.RemoteActions;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.HashMap;

public class AppViewModel extends ViewModel {
    private final MutableLiveData<NavController> _navController = new MutableLiveData<NavController>();
    private final MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>> _remoteSettings = new MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>>();
    private final MutableLiveData<RemoteActions> _remoteActions = new MutableLiveData<RemoteActions>();
    private final MutableLiveData<HttpClient> _remoteClient = new MutableLiveData<HttpClient>();

    public void setNavController(NavController controller){
        this._navController.postValue(controller);
    }
    public void setRemoteSettings(HashMap<String, FirebaseRemoteConfigValue> settings){
        this._remoteSettings.postValue(settings);
    }
    public void setRemoteActions(RemoteActions actions){
        this._remoteActions.postValue(actions);
    }
    public void setRemoteClient(HttpClient client){
        this._remoteClient.postValue(client);
    }

    public LiveData<NavController> navController = _navController;
    public LiveData<HashMap<String, FirebaseRemoteConfigValue>> remoteSettings = _remoteSettings;
    public LiveData<RemoteActions> remoteActions = _remoteActions;
    public LiveData<HttpClient> remoteClient = _remoteClient;
}
