package com.foreverdevelopers.doctors_directory_kenya.data.repository;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.foreverdevelopers.doctors_directory_kenya.callback.RemoteCallback;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;
import com.foreverdevelopers.doctors_directory_kenya.remote.Remote;
import com.foreverdevelopers.doctors_directory_kenya.remote.Responses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CountyRepo {
    private final Remote remote;
    private final String baseUrl, countiesAll, countiesByService, countiesByFacility;
    private Remote.RequestProcessor requestProcessor;
    private final CountyViewModel countyViewModel;
    public CountyRepo(AppCompatActivity context,
                      Remote remote,
                      String baseUrl,
                      String countiesAll,
                      String countiesByService,
                      String countiesByFacility){
        this.remote = remote;
        this.baseUrl = baseUrl;
        this.countiesAll = countiesAll;
        this.countiesByService = countiesByService;
        this.countiesByFacility = countiesByFacility;
        this.countyViewModel = new ViewModelProvider(context).get(CountyViewModel.class);
    }
    public void counties(){
        String url = baseUrl+countiesAll;
        requestProcessor = url.trim().startsWith("https://") ?
                remote.secureRequest :
                (url.trim().startsWith("http://") ?
                        remote.unsecureRequest :
                        null
                );
        if(null==requestProcessor) return;
        requestProcessor.processRequest("get", url, null, null, new RemoteCallback() {
            @Override
            public void onSuccess(String result) {
                if(null==result || result.trim().length() == 0) return;
                Responses.MainResponse mainResponse = Responses.mainResponse(result);
                if(null==mainResponse || mainResponse.code < 0) return;
                try{
                    ArrayList<County> counties = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        County county = new County();
                        county.name = mainResponse.data.getString(i);
                        counties.add(county);
                    }
                    countyViewModel.addAll(counties);
                    countyViewModel.setFilteredCounties(counties);
                }catch (JSONException ex){
                    Log.e(SYSTAG, ex.getLocalizedMessage());
                }
            }

            @Override
            public void onIOException(IOException ioException) {
                Log.e(SYSTAG, ioException.getLocalizedMessage());
            }

            @Override
            public void onServerError(JSONObject serverError) {
                Log.e(SYSTAG, serverError.toString());
            }

            @Override
            public void onJSONException(JSONException jsonException) {
                Log.e(SYSTAG, jsonException.getLocalizedMessage());
            }
        });
    }
    public void countiesByFacility(String facility){
        String url = baseUrl+countiesByFacility+"/"+facility;
        requestProcessor = url.trim().startsWith("https://") ?
                remote.secureRequest :
                (url.trim().startsWith("http://") ?
                        remote.unsecureRequest :
                        null
                );
        if(null==requestProcessor) return;
        requestProcessor.processRequest("get", url, null, null, new RemoteCallback() {
            @Override
            public void onSuccess(String result) {
                if(null==result || result.trim().length() == 0) return;
                Responses.MainResponse mainResponse = Responses.mainResponse(result);
                if(null==mainResponse || mainResponse.code < 0) return;
                try{
                    ArrayList<County> counties = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        County county = new County();
                        county.name = mainResponse.data.getString(i);
                        counties.add(county);
                    }
                    countyViewModel.setFilteredCounties(counties);
                }catch (JSONException ex){
                    Log.e(SYSTAG, ex.getLocalizedMessage());
                }
            }

            @Override
            public void onIOException(IOException ioException) {
                Log.e(SYSTAG, ioException.getLocalizedMessage());
            }

            @Override
            public void onServerError(JSONObject serverError) {
                Log.e(SYSTAG, serverError.toString());
            }

            @Override
            public void onJSONException(JSONException jsonException) {
                Log.e(SYSTAG, jsonException.getLocalizedMessage());
            }
        });
    }
    public void countiesByService(String service){
        String url = baseUrl+countiesByService+"/"+service;
        requestProcessor = url.trim().startsWith("https://") ?
                remote.secureRequest :
                (url.trim().startsWith("http://") ?
                        remote.unsecureRequest :
                        null
                );
        if(null==requestProcessor) return;
        requestProcessor.processRequest("get", url, null, null, new RemoteCallback() {
            @Override
            public void onSuccess(String result) {
                if(null==result || result.trim().length() == 0) return;
                Responses.MainResponse mainResponse = Responses.mainResponse(result);
                if(null==mainResponse || mainResponse.code < 0) return;
                try{
                    ArrayList<County> counties = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        County county = new County();
                        county.name = mainResponse.data.getString(i);
                        counties.add(county);
                    }
                    countyViewModel.setFilteredCounties(counties);
                }catch (JSONException ex){
                    Log.e(SYSTAG, ex.getLocalizedMessage());
                }
            }

            @Override
            public void onIOException(IOException ioException) {
                Log.e(SYSTAG, ioException.getLocalizedMessage());
            }

            @Override
            public void onServerError(JSONObject serverError) {
                Log.e(SYSTAG, serverError.toString());
            }

            @Override
            public void onJSONException(JSONException jsonException) {
                Log.e(SYSTAG, jsonException.getLocalizedMessage());
            }
        });
    }
}
