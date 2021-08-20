package com.foreverdevelopers.m_daktari;

import android.os.Bundle;
import android.os.Process;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.foreverdevelopers.m_daktari.data.HttpClient;
import com.foreverdevelopers.m_daktari.remote.Remote;
import com.foreverdevelopers.m_daktari.remote.Requests;
import com.foreverdevelopers.m_daktari.util.InitializeApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.HashMap;
import java.util.Objects;

public class AppActivity extends AppCompatActivity {

    private NavController navController;
    private AppViewModel appViewModel;

    private final FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    private final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeApp();
        viewListeners();
    }
    private void loadComponents(){
        setContentView(R.layout.activity_app);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_app);
        navController = navHostFragment.getNavController();
        appViewModel.setNavController(navController);
    }
    private void initializeApp(){
        appViewModel = new ViewModelProvider(this).get(AppViewModel.class);
        InitializeApp.firebase(firebaseRemoteConfig, appViewModel, this);
    }
    private void viewListeners(){
        appViewModel.remoteSettings.observe(this, new Observer<HashMap<String, FirebaseRemoteConfigValue>>() {
            @Override
            public void onChanged(HashMap<String, FirebaseRemoteConfigValue> remoteConfigs) {
                String baseUrl = Objects.requireNonNull(remoteConfigs.get("base_url")).asString(),
                        doctorsAll = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        doctorsByFacility = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        doctorsByService = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        countiesAll = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        countiesByService = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        countiesByFacility = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        facilitiesAll = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        facilitiesByCounty = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        serviceAll = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        serviceByCounty = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString(),
                        serviceByFacility = Objects.requireNonNull(remoteConfigs.get("doctors_ep")).asString();
                final HttpClient appRemoteClient = InitializeApp.httpClient(AppActivity.this);
                final Remote appRemote = new Remote(appRemoteClient);
                final Requests appRequests = new Requests(appRemoteClient, appRemote, baseUrl,
                        doctorsAll, doctorsByFacility, doctorsByService,
                        countiesAll, countiesByService, countiesByFacility,
                        facilitiesAll, facilitiesByCounty,
                        serviceAll, serviceByCounty, serviceByFacility);
                appViewModel.setRemoteRequests(appRequests);
            }
        });
        appViewModel.remoteRequests.observe(this, new Observer<Requests>() {
            @Override
            public void onChanged(Requests httpClient) {
                loadComponents();
            }
        });
    }
    private void killApp(){
        Process.killProcess(Process.myPid());
        this.finish();
    }
}