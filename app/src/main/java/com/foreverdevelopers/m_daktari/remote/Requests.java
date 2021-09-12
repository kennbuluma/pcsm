package com.foreverdevelopers.m_daktari.remote;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.util.Log;

import com.foreverdevelopers.m_daktari.AppViewModel;
import com.foreverdevelopers.m_daktari.callback.RemoteCallback;
import com.foreverdevelopers.m_daktari.data.HttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;

public class Requests {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final int KEEP_ALIVE = 1;
    private final String baseUrl,
            doctorsAll, doctorsByFacility, doctorsByService, doctorsSearch,
            countiesAll, countiesByService, countiesByFacility,
            facilitiesAll, facilitiesByCounty,
            serviceAll, serviceByCounty, serviceByFacility;
    private final HttpClient httpClient;
    private final Remote remote;
    private Remote.RequestProcessor requestProcessor;
    private ExecutorService requestService = Executors.newFixedThreadPool(8);

    private AppViewModel appViewModel;

    public Requests(HttpClient httpClient,
                    Remote remote,
                    String baseUrl,
                    String doctorsAll,
                    String doctorsByFacility,
                    String doctorsByService,
                    String doctorsSearch,
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
        this.doctorsSearch = doctorsSearch;
        this.countiesAll = countiesAll;
        this.countiesByService = countiesByService;
        this.countiesByFacility = countiesByFacility;
        this.facilitiesAll = facilitiesAll;
        this.facilitiesByCounty = facilitiesByCounty;
        this.serviceAll = serviceAll;
        this.serviceByCounty = serviceByCounty;
        this.serviceByFacility = serviceByFacility;
    }
    public void runTest(){
        //requestService.submit()
    }

    public void setAppViewModel(AppViewModel viewModel){
        this.appViewModel = viewModel;
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
                        appViewModel.setCounties(counties);
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
                        appViewModel.setCounties(counties);
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
                        appViewModel.setCounties(counties);
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
                        appViewModel.setFacilities(facilities);
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
                        appViewModel.setFacilities(facilities);
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
                        appViewModel.setServices(services);
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
                    Log.w(SYSTAG, result);
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<String> services = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            services.add(mainResponse.data.getString(i));
                        }
                        appViewModel.setServices(services);
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
                    Log.w(SYSTAG, result);
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<String> services = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            services.add(mainResponse.data.getString(i));
                        }
                        appViewModel.setServices(services);
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
                    Log.w(SYSTAG, result);
                    Responses.MainResponse mainResponse = Responses.mainResponse(result);
                    if(null==mainResponse || mainResponse.code < 0) return;
                    try{
                        ArrayList<Object> doctors = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            doctors.add(Responses.doctorsResponse(mainResponse.data.getJSONObject(i)));
                        }
                        appViewModel.setAllDoctors(doctors);
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
    public void doctorsSearch(JSONObject searchValue){
        String url = baseUrl+doctorsSearch;
        requestProcessor = url.trim().startsWith("https://") ?
                remote.secureRequest :
                (url.trim().startsWith("http://") ?
                        remote.unsecureRequest :
                        null
                );
        if(null==requestProcessor) return;
        requestProcessor.processRequest("post", url, searchValue, null, new RemoteCallback() {
            @Override
            public void onSuccess(String result) {
                if(null==result || result.trim().length() == 0) return;
                Log.w(SYSTAG, result);
                Responses.MainResponse mainResponse = Responses.mainResponse(result);
                if(null==mainResponse || mainResponse.code < 0) return;
                try{
                    ArrayList<Object> doctors = new ArrayList<>();
                    for(int i = 0; i < mainResponse.data.length(); i ++){
                        doctors.add(Responses.doctorsResponse(mainResponse.data.getJSONObject(i)));
                    }
                    appViewModel.setAllDoctors(doctors);
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
                        ArrayList<Object> doctors = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            doctors.add(mainResponse.data.getString(i));
                        }
                        appViewModel.setAllDoctors(doctors);
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
                        ArrayList<Object> doctors = new ArrayList<>();
                        for(int i = 0; i < mainResponse.data.length(); i ++){
                            doctors.add(mainResponse.data.getString(i));
                        }
                        appViewModel.setAllDoctors(doctors);
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
    class RequestExecutorRunnable implements Runnable{
        @Override
        public void run() {
            //
        }
    }
}
