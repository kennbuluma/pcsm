package com.foreverdevelopers.doctors_directory_kenya;

import android.os.Bundle;
import android.os.Process;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.HttpClient;
import com.foreverdevelopers.doctors_directory_kenya.remote.Remote;
import com.foreverdevelopers.doctors_directory_kenya.remote.Requests;
import com.foreverdevelopers.doctors_directory_kenya.util.InitializeApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.ArrayList;
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
        appViewModel.dbInstance.observe(this, new Observer<DataDB>() {
            @Override
            public void onChanged(DataDB dataDB) {
                InitializeApp.UpdateLocalData(dataDB);
                loadComponents();
            }
        });
        appViewModel.remoteSettings.observe(this, new Observer<HashMap<String, FirebaseRemoteConfigValue>>() {
            @Override
            public void onChanged(HashMap<String, FirebaseRemoteConfigValue> remoteConfigs) {
                String baseUrl = Objects.requireNonNull(remoteConfigs.get("base_url")).asString(),
                        doctorsAll = Objects.requireNonNull(remoteConfigs.get("doctors_all_ep")).asString(),
                        doctorsByFacility = Objects.requireNonNull(remoteConfigs.get("doctors_facility_ep")).asString(),
                        doctorsByService = Objects.requireNonNull(remoteConfigs.get("doctors_service_ep")).asString(),
                        doctorsSearch = Objects.requireNonNull(remoteConfigs.get("doctors_search_ep")).asString(),
                        countiesAll = Objects.requireNonNull(remoteConfigs.get("counties_all_ep")).asString(),
                        countiesByService = Objects.requireNonNull(remoteConfigs.get("counties_service_ep")).asString(),
                        countiesByFacility = Objects.requireNonNull(remoteConfigs.get("counties_facility_ep")).asString(),
                        facilitiesAll = Objects.requireNonNull(remoteConfigs.get("facilities_all_ep")).asString(),
                        facilitiesByCounty = Objects.requireNonNull(remoteConfigs.get("facilities_county_ep")).asString(),
                        serviceAll = Objects.requireNonNull(remoteConfigs.get("services_all_ep")).asString(),
                        serviceByCounty = Objects.requireNonNull(remoteConfigs.get("services_county_ep")).asString(),
                        serviceByFacility = Objects.requireNonNull(remoteConfigs.get("services_facility_ep")).asString();
                final HttpClient appRemoteClient = InitializeApp.httpClient(AppActivity.this);
                final Remote appRemote = new Remote(appRemoteClient);
                final Requests appRequests = new Requests(appRemoteClient, appRemote, baseUrl,
                        doctorsAll, doctorsByFacility, doctorsByService, doctorsSearch,
                        countiesAll, countiesByService, countiesByFacility,
                        facilitiesAll, facilitiesByCounty,
                        serviceAll, serviceByCounty, serviceByFacility);
                appViewModel.setRemoteRequests(appRequests);
            }
        });
        appViewModel.remoteRequests.observe(this, new Observer<Requests>() {
            @Override
            public void onChanged(Requests request) {
                request.setAppViewModel(appViewModel);
                Room.databaseBuilder(getApplicationContext(),DataDB.class,"mdkt-db").build();
            }
        });
    }
    private void killApp(){
        Process.killProcess(Process.myPid());
        this.finish();
    }
}