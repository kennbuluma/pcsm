package com.foreverdevelopers.m_daktari;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import com.foreverdevelopers.m_daktari.data.ActivePath;
import com.foreverdevelopers.m_daktari.data.FireMessageSendError;
import com.foreverdevelopers.m_daktari.remote.Requests;
import com.google.firebase.messaging.RemoteMessage;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.HashMap;

public class AppViewModel extends ViewModel {
    private final MutableLiveData<NavController> _navController = new MutableLiveData<NavController>();
    public LiveData<NavController> navController = _navController;

    private final MutableLiveData<HashMap<Integer, ActivePath>> _activePathMap = new MutableLiveData<>();
    public LiveData<HashMap<Integer, ActivePath>> activePathMap = _activePathMap;

    private final MutableLiveData<String> _activeBaseItem = new MutableLiveData<>();
    public LiveData<String> activeBaseItem = _activeBaseItem;

    private final MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>> _remoteSettings = new MutableLiveData<HashMap<String, FirebaseRemoteConfigValue>>();
    public LiveData<HashMap<String, FirebaseRemoteConfigValue>> remoteSettings = _remoteSettings;

    private final MutableLiveData<Requests> _remoteRequests = new MutableLiveData<Requests>();
    public LiveData<Requests> remoteRequests = _remoteRequests;

    private final MutableLiveData<String> _fireMessageToken = new MutableLiveData<>();
    public LiveData<String> fireMessageToken = _fireMessageToken;

    private final MutableLiveData<String> _fireMessage = new MutableLiveData<>();
    public LiveData<String> fireMessage = _fireMessage;

    private final MutableLiveData<RemoteMessage> _fireRemoteMessage = new MutableLiveData<>();
    public LiveData<RemoteMessage> fireRemoteMessage = _fireRemoteMessage;

    private final MutableLiveData<FireMessageSendError> _fireMessageSendError = new MutableLiveData<>();
    public LiveData<FireMessageSendError> fireMessageSendError = _fireMessageSendError;

    public void setNavController(NavController controller){ this._navController.postValue(controller); }
    public void setActivePathMap(HashMap<Integer, ActivePath> pathMap){ this._activePathMap.postValue(pathMap); }
    public void setActiveBaseItem(String baseItem){ this._activeBaseItem.postValue(baseItem); }
    public void setRemoteSettings(HashMap<String, FirebaseRemoteConfigValue> settings){ this._remoteSettings.postValue(settings); }
    public void setRemoteRequests(Requests requests){ this._remoteRequests.postValue(requests); }
    public void setFireMessageToken(String token){ this._fireMessageToken.postValue(token); }
    public void setFireMessageSent(String message){ this._fireMessage.postValue(message); }
    public void setFireMessage(RemoteMessage remoteMessage){ this._fireRemoteMessage.postValue(remoteMessage); }
    public void setFireMessageSendError(FireMessageSendError fireSendError){ this._fireMessageSendError.postValue(fireSendError); }
}
