package com.foreverdevelopers.m_daktari.remote;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.util.Log;

import com.foreverdevelopers.m_daktari.callback.RemoteCallback;
import com.foreverdevelopers.m_daktari.data.HttpClient;
import com.foreverdevelopers.m_daktari.data.entity.Doctor;
import com.foreverdevelopers.m_daktari.ui.CountiesListViewModel;
import com.foreverdevelopers.m_daktari.ui.DoctorsListViewModel;
import com.foreverdevelopers.m_daktari.ui.FacilitiesListViewModel;
import com.foreverdevelopers.m_daktari.ui.ServicesListViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Requests {
    private final String baseUrl,
            doctorsAll, doctorsByFacility, doctorsByService,
            countiesAll, countiesByService, countiesByFacility,
            facilitiesAll, facilitiesByCounty,
            serviceAll, serviceByCounty, serviceByFacility;
    private final HttpClient httpClient;
    private final Remote remote;
    private Remote.RequestProcessor requestProcessor;

    private CountiesListViewModel countiesViewModel;
    private FacilitiesListViewModel facilitiesViewModel;
    private ServicesListViewModel servicesViewModel;
    private DoctorsListViewModel doctorsViewModel;
    public Requests(HttpClient httpClient,
                    Remote remote,
                    String baseUrl,
                    String doctorsAll,
                    String doctorsByFacility,
                    String doctorsByService,
                    String countiesAll,
                    String countiesByService,
                    String countiesByFacility,
                    String facilitiesAll,
                    String facilitiesByCounty,
                    String serviceAll,
                    String serviceByCounty,
                    String serviceByFacility){
        this.httpClient = httpClient;
        this.remote = remote;
        this.baseUrl = baseUrl;
        this.doctorsAll = doctorsAll;
        this.doctorsByFacility = doctorsByFacility;
        this.doctorsByService = doctorsByService;
        this.countiesAll = countiesAll;
        this.countiesByService = countiesByService;
        this.countiesByFacility = countiesByFacility;
        this.facilitiesAll = facilitiesAll;
        this.facilitiesByCounty = facilitiesByCounty;
        this.serviceAll = serviceAll;
        this.serviceByCounty = serviceByCounty;
        this.serviceByFacility = serviceByFacility;
    }

    public void setCountiesViewModel(CountiesListViewModel viewModel){
        this.countiesViewModel = viewModel;
    }
    public void setFacilitiesViewModel(FacilitiesListViewModel viewModel){
        this.facilitiesViewModel = viewModel;
    }
    public void setServicesViewModel(ServicesListViewModel viewModel){
        this.servicesViewModel = viewModel;
    }
    public void setDoctorsViewModel(DoctorsListViewModel viewModel){
        this.doctorsViewModel = viewModel;
    }

    public void countiesAll(){
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
                        ArrayList<String> counties = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            counties.add(mainResponse.data.getString(i));
                        }
                        countiesViewModel.setCounties(counties);
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
                        ArrayList<String> counties = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            counties.add(mainResponse.data.getString(i));
                        }
                        countiesViewModel.setCounties(counties);
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
                        ArrayList<String> counties = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            counties.add(mainResponse.data.getString(i));
                        }
                        countiesViewModel.setCounties(counties);
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
    public void facilitiesAll(){
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
                        ArrayList<String> facilities = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            facilities.add(mainResponse.data.getString(i));
                        }
                        facilitiesViewModel.setFacilities(facilities);
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
        public void facilitiesByCounty(String county){
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
                        ArrayList<String> facilities = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            facilities.add(mainResponse.data.getString(i));
                        }
                        facilitiesViewModel.setFacilities(facilities);
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
    public void servicesAll(){
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
                        ArrayList<String> services = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            services.add(mainResponse.data.getString(i));
                        }
                        servicesViewModel.setServices(services);
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
        public void servicesByFacility(String facility){
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
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<String> services = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            services.add(mainResponse.data.getString(i));
                        }
                        servicesViewModel.setServices(services);
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
        public void servicesByCounty(String county){
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
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<String> services = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            services.add(mainResponse.data.getString(i));
                        }
                        servicesViewModel.setServices(services);
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
        public void doctorsAll(){
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
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<Doctor> doctors = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            doctors.add(Responses.doctorsResponse(mainResponse.data.getJSONObject(i)));
                        }
                        doctorsViewModel.setDoctors(doctors);
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
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<Doctor> doctors = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            doctors.add(Responses.doctorsResponse(mainResponse.data.getJSONObject(i)));
                        }
                        doctorsViewModel.setDoctors(doctors);
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
                            doctors.add(Responses.doctorsResponse(mainResponse.data.getJSONObject(i)));
                        }
                        doctorsViewModel.setDoctors(doctors);
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
