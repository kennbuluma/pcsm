package com.foreverdevelopers.m_daktari;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.data.FireMessageSendError;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.remote.Requests;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.ArrayList;
import java.util.HashMap;

public class AppViewModel extends ViewModel {
    private final MutableLiveData<Integer> _currentIndex = new MutableLiveData<Integer>();
    public LiveData<Integer> currentIndex = _currentIndex;

    private final MutableLiveData<NavController> _navController = new MutableLiveData<NavController>();
    public LiveData<NavController> navController = _navController;

    private final MutableLiveData<HashMap<Integer, ActivePath>> _activePathMap = new MutableLiveData<>();
    public LiveData<HashMap<Integer, ActivePath>> activePathMap = _activePathMap;

    private final MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>> _remoteSettings = new MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>>();
    public LiveData<HashMap<String, FirebaseRemoteConfigValue>> remoteSettings = _remoteSettings;

    private final MutableLiveData<Requests> _remoteRequests = new MutableLiveData<Requests>();
    public LiveData<Requests> remoteRequests = _remoteRequests;

    private final MutableLiveData<ArrayList<Object>> _allDoctors = new MutableLiveData<ArrayList<Object>>();
    public LiveData<ArrayList<Object>> allDoctors = _allDoctors;

    private final MutableLiveData<String> _fireMessageToken = new MutableLiveData<>();
    public LiveData<String> fireMessageToken = _fireMessageToken;

    private final MutableLiveData<String> _fireMessage = new MutableLiveData<>();
    public LiveData<String> fireMessage = _fireMessage;

    private final MutableLiveData<RemoteMessage> _fireRemoteMessage = new MutableLiveData<>();
    public LiveData<RemoteMessage> fireRemoteMessage = _fireRemoteMessage;

    private final MutableLiveData<FireMessageSendError> _fireMessageSendError = new MutableLiveData<>();
    public LiveData<FireMessageSendError> fireMessageSendError = _fireMessageSendError;

    private MutableLiveData<ArrayList<String>> _services = new MutableLiveData<>();
    public LiveData<ArrayList<String>> services = _services;

    private MutableLiveData<ArrayList<String>> _facilities = new MutableLiveData<>();
    public LiveData<ArrayList<String>> facilities = _facilities;

    private final MutableLiveData<Doctor> _doctor = new MutableLiveData<>();
    public LiveData<Doctor> doctor = _doctor;

    private MutableLiveData<ArrayList<String>> _counties = new MutableLiveData<>();
    public LiveData<ArrayList<String>> counties = _counties;

    public void setCounties(ArrayList<String> counties){ _counties.postValue(counties); }
    public void setFacilities(ArrayList<String> facilities){ _facilities.postValue(facilities); }
    public void setDoctor(Doctor doctor){ this._doctor.postValue(doctor); }
    public void setServices(ArrayList<String> services){ _services.postValue(services); }
    public void setCurrentIndex(Integer currentIndex){ this._currentIndex.postValue(currentIndex); }
    public void setAllDoctors(ArrayList<Object> doctors){ this._allDoctors.postValue(doctors); }
    public void setNavController(NavController controller){ this._navController.postValue(controller); }
    public void setActivePathMap(HashMap<Integer, ActivePath> pathMap){ this._activePathMap.postValue(pathMap); }
    public void setRemoteSettings(HashMap<String, FirebaseRemoteConfigValue> settings){ this._remoteSettings.postValue(settings); }
    public void setRemoteRequests(Requests requests){ this._remoteRequests.postValue(requests); }
    public void setFireMessageToken(String token){ this._fireMessageToken.postValue(token); }
    public void setFireMessageSent(String message){ this._fireMessage.postValue(message); }
    public void setFireMessage(RemoteMessage remoteMessage){ this._fireRemoteMessage.postValue(remoteMessage); }
    public void setFireMessageSendError(FireMessageSendError fireSendError){ this._fireMessageSendError.postValue(fireSendError); }

}
