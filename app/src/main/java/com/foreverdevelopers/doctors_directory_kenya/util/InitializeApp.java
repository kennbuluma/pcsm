package com.foreverdevelopers.doctors_directory_kenya.util;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.foreverdevelopers.doctors_directory_kenya.AppViewModel;
import com.foreverdevelopers.doctors_directory_kenya.R;
import com.foreverdevelopers.doctors_directory_kenya.data.DataDB;
import com.foreverdevelopers.doctors_directory_kenya.data.HttpClient;
import com.foreverdevelopers.doctors_directory_kenya.data.SSLItem;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.CountyDao;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.DoctorDao;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.FacilityDao;
import com.foreverdevelopers.doctors_directory_kenya.data.dao.ServiceDao;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

public class InitializeApp {
    public static HttpClient httpClient(Context context) {
        try{
            final SSLItem certItems = new SSLOps().getSSLFactory(context, null);
            final ConnectionPool pool = new ConnectionPool(5, 120, TimeUnit.SECONDS);
            final ArrayList<ConnectionSpec> unsecureConnectionSpecs = new ArrayList<ConnectionSpec>();
            unsecureConnectionSpecs.add(ConnectionSpec.CLEARTEXT);
            unsecureConnectionSpecs.add(ConnectionSpec.RESTRICTED_TLS);
            unsecureConnectionSpecs.add(ConnectionSpec.MODERN_TLS);
            unsecureConnectionSpecs.add(ConnectionSpec.COMPATIBLE_TLS);
            final OkHttpClient unsecureClient = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .connectionSpecs(unsecureConnectionSpecs)
                    .connectionPool(pool)
                    .build();
            if(null==certItems ||
               null==certItems.sslSocketFactory ||
               null==certItems.trustManager) return new HttpClient(null, unsecureClient);
            final ArrayList<ConnectionSpec> secureConnectionSpecs = new ArrayList<ConnectionSpec>();
            secureConnectionSpecs.add(ConnectionSpec.RESTRICTED_TLS);
            secureConnectionSpecs.add(ConnectionSpec.MODERN_TLS);
            secureConnectionSpecs.add(ConnectionSpec.COMPATIBLE_TLS);
            final OkHttpClient secureClient = new OkHttpClient.Builder()
                    .sslSocketFactory(certItems.sslSocketFactory, certItems.trustManager)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .connectionSpecs(secureConnectionSpecs)
                    .connectionPool(pool)
                    .build();
            return new HttpClient(secureClient, unsecureClient);
        }
        catch (NoSuchAlgorithmException ev){
            return null;
        }
        catch (CertificateException ew){
            return null;
        }
        catch (KeyManagementException ex){
            return null;
        }
        catch (KeyStoreException ey){
            return null;
        }
        catch (IOException ez){
            return null;
        }
    }
    public static void firebase(FirebaseRemoteConfig remoteConfig,
                                AppViewModel appViewModel,
                                Activity activity){
        final FirebaseRemoteConfigSettings.Builder configSettings = new FirebaseRemoteConfigSettings.Builder();
        configSettings.setFetchTimeoutInSeconds(3600*2);
        remoteConfig.setConfigSettingsAsync(configSettings.build());
        fetchFireConfig(remoteConfig, appViewModel, activity);
        final Map<String, Object> defaultConfig = (Map<String, Object>) defaultConfigs((Context) activity);
        remoteConfig.setDefaultsAsync(defaultConfig);
    }
    public static void UpdateLocalData(DataDB db){
        fetchCounties(db.countyDao());
        fetchFacilities(db.facilityDao());
        fetchServices(db.serviceDao());
        fetchDoctors(db.doctorDao());
    }
    private static void fetchFireConfig(FirebaseRemoteConfig remoteConfig,
                                        AppViewModel appViewModel,
                                        Activity activity){
        remoteConfig.fetchAndActivate().addOnCompleteListener(activity, new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if(task.isSuccessful()){
                    updateRemoteConfig(remoteConfig, appViewModel);
                }
            }
        });
    }
    private static Map<String, Object> defaultConfigs(Context context){
        final HashMap<String,Object> remoteConfigs = new HashMap<String, Object>();
        remoteConfigs.put("base_url", context.getResources().getString(R.string.base_url));
        remoteConfigs.put("doctors_all_ep", context.getResources().getString(R.string.doctors_all_ep));
        remoteConfigs.put("doctors_facility_ep", context.getResources().getString(R.string.doctors_facility_ep));
        remoteConfigs.put("doctors_service_ep", context.getResources().getString(R.string.doctors_service_ep));
        remoteConfigs.put("counties_all_ep", context.getResources().getString(R.string.counties_all_ep));
        remoteConfigs.put("counties_service_ep", context.getResources().getString(R.string.counties_service_ep));
        remoteConfigs.put("counties_facility_ep", context.getResources().getString(R.string.counties_facility_ep));
        remoteConfigs.put("facilities_all_ep", context.getResources().getString(R.string.facilities_all_ep));
        remoteConfigs.put("facilities_county_ep", context.getResources().getString(R.string.facilities_county_ep));
        remoteConfigs.put("services_all_ep", context.getResources().getString(R.string.services_all_ep));
        remoteConfigs.put("services_county_ep", context.getResources().getString(R.string.services_county_ep));
        remoteConfigs.put("services_facility_ep", context.getResources().getString(R.string.services_facility_ep));
        remoteConfigs.put("counties_title", context.getResources().getString(R.string.lbl_counties));
        remoteConfigs.put("facilities_title", context.getResources().getString(R.string.lbl_facilities));
        remoteConfigs.put("services_title", context.getResources().getString(R.string.lbl_services));
        remoteConfigs.put("doctors_title", context.getResources().getString(R.string.lbl_doctors_list));
        return remoteConfigs;
    }
    private static void updateRemoteConfig(FirebaseRemoteConfig remoteConfig, AppViewModel appViewModel){
        final Map<String, FirebaseRemoteConfigValue> currentConfig = remoteConfig.getAll();
        appViewModel.setRemoteSettings((HashMap<String, FirebaseRemoteConfigValue>) currentConfig);
    }
    private static void fetchCounties(CountyDao countyDao){
        ArrayList<County> counties = new ArrayList<>();
        countyDao.addAllCounties(counties);
    }
    private static void fetchFacilities(FacilityDao facilityDao){
        ArrayList<Facility> facilities = new ArrayList<>();
        facilityDao.addAllFacility(facilities);
    }
    private static void fetchServices(ServiceDao serviceDao){
        ArrayList<Service> services = new ArrayList<>();
        serviceDao.addAllServices(services);
    }
    private static void fetchDoctors(DoctorDao doctorDao){
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        doctorDao.addAllDoctor(doctors);
    }
}
