package com.foreverdevelopers.doctors_directory_kenya;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.foreverdevelopers.doctors_directory_kenya.data.FireMessageSendError;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
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
    private final MutableLiveData<NavController> _navController = new MutableLiveData<NavController>();
    private final MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>> _remoteSettings = new MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>>();
    private final MutableLiveData<String> _fireMessageToken = new MutableLiveData<>();
    private final MutableLiveData<String> _fireMessage = new MutableLiveData<>();
    private final MutableLiveData<RemoteMessage> _fireRemoteMessage = new MutableLiveData<>();
    private final MutableLiveData<FireMessageSendError> _fireMessageSendError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _showProgress = new MutableLiveData<>();

    public LiveData<CountyRepo> countyRepo = this._countyRepo;
    public LiveData<FacilityRepo> facilityRepo = this._facilityRepo;
    public LiveData<ServiceRepo> serviceRepo = this._serviceRepo;
    public LiveData<DoctorRepo> doctorRepo = this._doctorRepo;
    public LiveData<NavController> navController = this._navController;
    public LiveData<HashMap<String, FirebaseRemoteConfigValue>> remoteSettings = this._remoteSettings;
    public LiveData<String> fireMessageToken = this._fireMessageToken;
    public LiveData<String> fireMessage = this._fireMessage;
    public LiveData<RemoteMessage> fireRemoteMessage = this._fireRemoteMessage;
    public LiveData<FireMessageSendError> fireMessageSendError = this._fireMessageSendError;
    public LiveData<Boolean> showProgress = this._showProgress;

    public void setCountyRepo(CountyRepo countyRepo){ this._countyRepo.postValue(countyRepo); }
    public void setFacilityRepo(FacilityRepo facilityRepo){ this._facilityRepo.postValue(facilityRepo); }
    public void setServiceRepo(ServiceRepo serviceRepo){ this._serviceRepo.postValue(serviceRepo); }
    public void setDoctorRepo(DoctorRepo doctorRepo){ this._doctorRepo.postValue(doctorRepo); }
    public void setNavController(NavController controller){ this._navController.postValue(controller); }
    public void setRemoteSettings(HashMap<String, FirebaseRemoteConfigValue> settings){ this._remoteSettings.postValue(settings); }
    public void setFireMessageToken(String token){ this._fireMessageToken.postValue(token); }
    public void setFireMessageSent(String message){ this._fireMessage.postValue(message); }
    public void setFireMessage(RemoteMessage remoteMessage){ this._fireRemoteMessage.postValue(remoteMessage); }
    public void setFireMessageSendError(FireMessageSendError fireSendError){ this._fireMessageSendError.postValue(fireSendError); }
    public void setShowProgress(Boolean show){ this._showProgress.postValue(show);}
}
