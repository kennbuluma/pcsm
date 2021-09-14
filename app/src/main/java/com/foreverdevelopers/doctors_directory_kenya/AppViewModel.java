package com.foreverdevelopers.doctors_directory_kenya;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.foreverdevelopers.doctors_directory_kenya.data.ActivePath;
import com.foreverdevelopers.doctors_directory_kenya.data.FireMessageSendError;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.CountyRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.DoctorRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.FacilityRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.ServiceRepo;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.HashMap;

public class AppViewModel extends ViewModel {
    private final MutableLiveData<CountyRepo> _countyRepo = new MutableLiveData<CountyRepo>();
    private final MutableLiveData<FacilityRepo> _facilityRepo= new MutableLiveData<FacilityRepo>();
    private final MutableLiveData<ServiceRepo> _serviceRepo = new MutableLiveData<ServiceRepo>();
    private final MutableLiveData<DoctorRepo> _doctorRepo = new MutableLiveData<DoctorRepo>();
    private final MutableLiveData<Integer> _currentIndex = new MutableLiveData<Integer>();
    private final MutableLiveData<NavController> _navController = new MutableLiveData<NavController>();
    private final MutableLiveData<HashMap<Integer, ActivePath>> _activePathMap = new MutableLiveData<>();
    private final MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>> _remoteSettings = new MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>>();
    private final MutableLiveData<String> _fireMessageToken = new MutableLiveData<>();
    private final MutableLiveData<String> _fireMessage = new MutableLiveData<>();
    private final MutableLiveData<RemoteMessage> _fireRemoteMessage = new MutableLiveData<>();
    private final MutableLiveData<FireMessageSendError> _fireMessageSendError = new MutableLiveData<>();

    public LiveData<CountyRepo> countyRepo = _countyRepo;
    public LiveData<FacilityRepo> facilityRepo = _facilityRepo;
    public LiveData<ServiceRepo> serviceRepo = _serviceRepo;
    public LiveData<DoctorRepo> doctorRepo = _doctorRepo;
    public LiveData<Integer> currentIndex = _currentIndex;
    public LiveData<NavController> navController = _navController;
    public LiveData<HashMap<Integer, ActivePath>> activePathMap = _activePathMap;
    public LiveData<HashMap<String, FirebaseRemoteConfigValue>> remoteSettings = _remoteSettings;
    public LiveData<String> fireMessageToken = _fireMessageToken;
    public LiveData<String> fireMessage = _fireMessage;
    public LiveData<RemoteMessage> fireRemoteMessage = _fireRemoteMessage;
    public LiveData<FireMessageSendError> fireMessageSendError = _fireMessageSendError;

    public void setCountyRepo(CountyRepo countyRepo){ this._countyRepo.postValue(countyRepo); }
    public void setFacilityRepo(FacilityRepo facilityRepo){ this._facilityRepo.postValue(facilityRepo); }
    public void setServiceRepo(ServiceRepo serviceRepo){ this._serviceRepo.postValue(serviceRepo); }
    public void setDoctorRepo(DoctorRepo doctorRepo){ this._doctorRepo.postValue(doctorRepo); }
    public void setCurrentIndex(Integer currentIndex){ this._currentIndex.postValue(currentIndex); }
    public void setNavController(NavController controller){ this._navController.postValue(controller); }
    public void setActivePathMap(HashMap<Integer, ActivePath> pathMap){ this._activePathMap.postValue(pathMap); }
    public void setRemoteSettings(HashMap<String, FirebaseRemoteConfigValue> settings){ this._remoteSettings.postValue(settings); }
    public void setFireMessageToken(String token){ this._fireMessageToken.postValue(token); }
    public void setFireMessageSent(String message){ this._fireMessage.postValue(message); }
    public void setFireMessage(RemoteMessage remoteMessage){ this._fireRemoteMessage.postValue(remoteMessage); }
    public void setFireMessageSendError(FireMessageSendError fireSendError){ this._fireMessageSendError.postValue(fireSendError); }
}
