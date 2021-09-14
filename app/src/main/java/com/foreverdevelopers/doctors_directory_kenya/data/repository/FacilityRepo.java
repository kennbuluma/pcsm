package com.foreverdevelopers.doctors_directory_kenya.data.repository;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.foreverdevelopers.doctors_directory_kenya.callback.RemoteCallback;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.FacilityViewModel;
import com.foreverdevelopers.doctors_directory_kenya.remote.Remote;
import com.foreverdevelopers.doctors_directory_kenya.remote.Responses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class FacilityRepo {
    private final Remote remote;
    private final String baseUrl, facilitiesAll, facilitiesByCounty;
    private Remote.RequestProcessor requestProcessor;
    private final FacilityViewModel facilityViewModel;
    public FacilityRepo(AppCompatActivity context,
                        Remote remote,
                        String baseUrl,
                        String facilitiesAll,
                        String facilitiesByCounty){
        this.remote = remote;
        this.baseUrl = baseUrl;
        this.facilitiesAll = facilitiesAll;
        this.facilitiesByCounty = facilitiesByCounty;
        this.facilityViewModel = new ViewModelProvider(context).get(FacilityViewModel.class);
    }
    public void facilities(){
        String url = baseUrl+facilitiesAll;
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
                    ArrayList<Facility> facilities = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Facility facility = new Facility();
                        facility.name = mainResponse.data.getString(i);
                        facilities.add(facility);
                    }
                    facilityViewModel.addAll(facilities);
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
    public void facilityByCounty(String county){
        String url = baseUrl+facilitiesByCounty+"/"+county;
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
                    ArrayList<Facility> facilities = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Facility facility = new Facility();
                        facility.name = mainResponse.data.getString(i);
                        facilities.add(facility);
                    }
                    facilityViewModel.setFilteredFacilities(facilities);
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
