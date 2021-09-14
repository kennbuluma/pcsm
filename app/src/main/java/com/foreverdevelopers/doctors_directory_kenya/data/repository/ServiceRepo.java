package com.foreverdevelopers.doctors_directory_kenya.data.repository;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.foreverdevelopers.doctors_directory_kenya.callback.RemoteCallback;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;
import com.foreverdevelopers.doctors_directory_kenya.data.viewmodel.ServiceViewModel;
import com.foreverdevelopers.doctors_directory_kenya.remote.Remote;
import com.foreverdevelopers.doctors_directory_kenya.remote.Responses;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ServiceRepo {
    private final Remote remote;
    private final String baseUrl, serviceAll, serviceByCounty, serviceByFacility;
    private Remote.RequestProcessor requestProcessor;
    private final ServiceViewModel serviceViewModel;
    public ServiceRepo(AppCompatActivity context,
                       Remote remote,
                       String baseUrl,
                       String serviceAll,
                       String serviceByCounty,
                       String serviceByFacility){
        this.remote = remote;
        this.baseUrl = baseUrl;
        this.serviceAll = serviceAll;
        this.serviceByCounty = serviceByCounty;
        this.serviceByFacility = serviceByFacility;
        this.serviceViewModel = new ViewModelProvider(context).get(ServiceViewModel.class);
    }
    public void services(){
        String url = baseUrl+serviceAll;
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
                    ArrayList<Service> services = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Service service = new Service();
                        service.name = mainResponse.data.getString(i);
                        services.add(service);
                    }
                    serviceViewModel.addAll(services);
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
    public void serviceByFacility(String facility){
        String url = baseUrl+serviceByFacility+"/"+facility;
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
                    ArrayList<Service> services = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Service service = new Service();
                        service.name = mainResponse.data.getString(i);
                        services.add(service);
                    }
                    serviceViewModel.setFilteredServices(services);
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
    public void serviceByCounty(String county){
        String url = baseUrl+serviceByCounty+"/"+county;
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
                    ArrayList<Service> services = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        Service service = new Service();
                        service.name = mainResponse.data.getString(i);
                        services.add(service);
                    }
                    serviceViewModel.setFilteredServices(services);
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
