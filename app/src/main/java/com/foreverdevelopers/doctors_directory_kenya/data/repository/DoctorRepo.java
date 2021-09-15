package com.foreverdevelopers.doctors_directory_kenya.data.repository;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.foreverdevelopers.doctors_directory_kenya.callback.RemoteCallback;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.CountyViewModel;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.DoctorViewModel;
import com.foreverdevelopers.doctors_directory_kenya.remote.Remote;
import com.foreverdevelopers.doctors_directory_kenya.remote.Responses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepo {
    private final Remote remote;
    private final String baseUrl, doctorsAll, doctorsByFacility, doctorsByService, doctorsSearch;
    private Remote.RequestProcessor requestProcessor;
    private final DoctorViewModel doctorViewModel;
    public DoctorRepo(AppCompatActivity context,
                      Remote remote,
                      String baseUrl,
                      String doctorsAll,
                      String doctorsByFacility,
                      String doctorsByService,
                      String doctorsSearch){
        this.remote = remote;
        this.baseUrl = baseUrl;
        this.doctorsAll = doctorsAll;
        this.doctorsByFacility = doctorsByFacility;
        this.doctorsByService = doctorsByService;
        this.doctorsSearch = doctorsSearch;
        this.doctorViewModel = new ViewModelProvider(context).get(DoctorViewModel.class);
    }
    public void doctors(){
        String url = baseUrl+doctorsAll;
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
                Log.w(SYSTAG, result);
                Responses.MainResponse mainResponse = Responses.mainResponse(result);
                if(null==mainResponse || mainResponse.code < 0) return;
                try{
                    ArrayList<Doctor> doctors = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        doctors.add(Responses.doctorsResponse(mainResponse.data.getJSONObject(i)));
                    }
                    doctorViewModel.addAll(doctors);
                    doctorViewModel.setFilteredDoctors(doctors);
                }catch (JSONException ex){
                    Log.e(SYSTAG, "DocFac\t"+ex.getLocalizedMessage());
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
    public void doctorsByFacility(String facility){
        String url = baseUrl+doctorsByFacility+"/"+facility;
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
                Log.w(SYSTAG, result);
                Responses.MainResponse mainResponse = Responses.mainResponse(result);
                if(null==mainResponse || mainResponse.code < 0) return;
                try{
                    ArrayList<Doctor> doctors = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Doctor doctor = new Doctor();
                        doctor.name = mainResponse.data.getString(i);
                        doctors.add(doctor);
                    }
                    doctorViewModel.setFilteredDoctors(doctors);
                }catch (JSONException ex){
                    Log.e(SYSTAG, "DocFac\t"+ex.getLocalizedMessage());
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
    public void doctorsByService(String service){
        String url = baseUrl+doctorsByService+"/"+service;
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
                    ArrayList<Doctor> doctors = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Doctor doctor = new Doctor();
                        doctor.name = mainResponse.data.getString(i);
                        doctors.add(doctor);
                    }
                    doctorViewModel.setFilteredDoctors(doctors);
                }catch (JSONException ex){
                    Log.e(SYSTAG, "DocFac\t"+ex.getLocalizedMessage());
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
