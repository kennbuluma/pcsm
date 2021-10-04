package com.foreverdevelopers.doctors_directory_kenya;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_FACILITY;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.RA_COUNTIES_BY_SERVICE;
import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.room.Room;

import com.foreverdevelopers.doctors_directory_kenya.adapter.CountiesAdapter;
import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.HttpClient;
import com.foreverdevelopers.doctors_directory_kenya.data.Indexor;
import com.foreverdevelopers.doctors_directory_kenya.data.PathData;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.CountyRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.DoctorRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.FacilityRepo;
import com.foreverdevelopers.doctors_directory_kenya.data.repository.ServiceRepo;
import com.foreverdevelopers.doctors_directory_kenya.remote.Remote;
import com.foreverdevelopers.doctors_directory_kenya.util.Common;
import com.foreverdevelopers.doctors_directory_kenya.util.InitializeApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AppActivity extends AppCompatActivity {

    private NavController navController;
    private AppViewModel appViewModel;
    private Indexor currentIndexor = null;

    private final FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
    private final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(permissionsGranted()){
            initializeApp();
            viewListeners();
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(Common.REQUIRED_PERMISSIONS, Common.PERMISSION_REQUESTCODE);
            }else{
                ActivityCompat.requestPermissions(
                        AppActivity.this,
                        Common.REQUIRED_PERMISSIONS,
                        Common.PERMISSION_REQUESTCODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Common.PERMISSION_REQUESTCODE){
            for(int code : grantResults){
                if(code != PackageManager.PERMISSION_GRANTED){
                    killApp();
                    return;
                }
            }
            initializeApp();
            viewListeners();
        }
    }

    @Override
    public void onBackPressed() {
        backer();
        //super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            backer();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void backer(){
        if(null==appViewModel) {
            super.onBackPressed();
            return;
        }
        appViewModel.currentPathMap.observe(AppActivity.this, new Observer<HashMap<Integer, PathData>>() {
            @Override
            public void onChanged(HashMap<Integer, PathData> integerPathDataHashMap) {
                if(null == integerPathDataHashMap || null == currentIndexor) {
                    AppActivity.super.onBackPressed();
                    return;
                }else{

                    if(currentIndexor.index > 0){
                        int currentIndex = currentIndexor.index - 1;
                        if(null == integerPathDataHashMap.get(currentIndex)) {
                            AppActivity.super.onBackPressed();
                            return;
                        }
                        Object data = integerPathDataHashMap.get(currentIndex).data;
                        appViewModel.setCurrentIndexor(new Indexor(currentIndex, data), integerPathDataHashMap);
                    }
                }
            }
        });
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
                Room.databaseBuilder(getApplicationContext(),DataDB.class,"mdkt-db").build();
                CountyRepo countyRepo = new CountyRepo(AppActivity.this,
                        appRemote, baseUrl, countiesAll,
                        countiesByService, countiesByFacility);
                FacilityRepo facilityRepo = new FacilityRepo(AppActivity.this,
                        appRemote, baseUrl, facilitiesAll, facilitiesByCounty);
                ServiceRepo serviceRepo = new ServiceRepo(AppActivity.this,
                        appRemote, baseUrl, serviceAll, serviceByCounty, serviceByFacility);
                DoctorRepo doctorRepo = new DoctorRepo(AppActivity.this,
                        appRemote, baseUrl, doctorsAll, doctorsByFacility,
                        doctorsByService, doctorsSearch);
                countyRepo.counties();
                facilityRepo.facilities();
                serviceRepo.services();
                doctorRepo.doctors();
                appViewModel.setCountyRepo(countyRepo);
                appViewModel.setFacilityRepo(facilityRepo);
                appViewModel.setServiceRepo(serviceRepo);
                appViewModel.setDoctorRepo(doctorRepo);
                loadComponents();
            }
        });
        appViewModel.currentPathIndex.observe(AppActivity.this, new Observer<Indexor>() {
            @Override
            public void onChanged(Indexor indexor) {
                currentIndexor = indexor;
            }
        });
    }
    private void killApp(){
        Process.killProcess(Process.myPid());
        this.finish();
    }
    private boolean permissionsGranted(){
        for(String permission: Common.REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(getBaseContext(), permission) != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
    }
}