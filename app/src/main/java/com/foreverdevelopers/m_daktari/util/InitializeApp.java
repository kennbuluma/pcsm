package com.foreverdevelopers.m_daktari.util;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.R;
import com.foreverdevelopers.m_daktari.data.HttpClient;
import com.foreverdevelopers.m_daktari.data.SSLItem;
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
            final ArrayList<ConnectionSpec> secureConnectionSpecs = new ArrayList<ConnectionSpec>();
            secureConnectionSpecs.add(ConnectionSpec.RESTRICTED_TLS);
            secureConnectionSpecs.add(ConnectionSpec.MODERN_TLS);
            secureConnectionSpecs.add(ConnectionSpec.COMPATIBLE_TLS);
            final ArrayList<ConnectionSpec> unsecureConnectionSpecs = new ArrayList<ConnectionSpec>();
            unsecureConnectionSpecs.add(ConnectionSpec.CLEARTEXT);
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
            final OkHttpClient unsecureClient = new OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(90, TimeUnit.SECONDS)
                    .connectionSpecs(unsecureConnectionSpecs)
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
    private static Map<String, String> defaultConfigs(Context context){
        final HashMap<String,String> remoteConfigs = new HashMap<String, String>();
        remoteConfigs.put("base_url", context.getResources().getString(R.string.base_url));
        remoteConfigs.put("counties_ep", context.getResources().getString(R.string.counties_ep));
        remoteConfigs.put("facilities_ep", context.getResources().getString(R.string.facilities_ep));
        remoteConfigs.put("services_ep", context.getResources().getString(R.string.services_ep));
        remoteConfigs.put("doctors_ep", context.getResources().getString(R.string.doctors_ep));
        remoteConfigs.put("counties_title", context.getResources().getString(R.string.lbl_counties));
        remoteConfigs.put("facilities_title", context.getResources().getString(R.string.lbl_facilities));
        remoteConfigs.put("services_title", context.getResources().getString(R.string.lbl_services));
        remoteConfigs.put("doctors_title", context.getResources().getString(R.string.lbl_doctors));
        return remoteConfigs;
    }
    private static void updateRemoteConfig(FirebaseRemoteConfig remoteConfig, AppViewModel appViewModel){
        final Map<String, FirebaseRemoteConfigValue> currentConfig = remoteConfig.getAll();
        appViewModel.setRemoteSettings((HashMap<String, FirebaseRemoteConfigValue>) currentConfig);
    }
}