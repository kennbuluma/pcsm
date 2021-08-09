package com.foreverdevelopers.m_daktari.remote;

import android.util.Log;

import com.foreverdevelopers.m_daktari.callback.RemoteCallback;
import com.foreverdevelopers.m_daktari.data.Header;
import com.foreverdevelopers.m_daktari.data.HttpClient;
import com.foreverdevelopers.m_daktari.util.Common;
import com.foreverdevelopers.m_daktari.util.Converter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

public class Remote {
    private HttpClient httpClient;
    public RequestProcessor secureRequest, unsecureRequest;

    public Remote(HttpClient httpClient){
        this.httpClient = httpClient;
        this.secureRequest = new RequestProcessor(this.httpClient.secureClient);
        this.unsecureRequest = new RequestProcessor(this.httpClient.unsecureClient);
    }

    class RequestProcessor{
        private OkHttpClient client;
        private Request mainRequest;

        public RequestProcessor(OkHttpClient client){
            this.client = client;
        }

        public void processRequest(
                String method,
                String url,
                JSONObject params,
                Header headers,
                RemoteCallback callback
        ){
            mainRequest = setRequest(method, url, params, headers);
            sendCall(client, mainRequest, callback);
        }
        public void processFileRequest(
                String method,
                String url,
                ArrayList<FormDataRequest> params,
                ArrayList<FormDataRequest> files,
                Header headers,
                RemoteCallback callback
        ){
            mainRequest = setFileRequest(method, url, params, files, headers);
            sendCall(client, mainRequest, callback);
        }

        private Request setRequest(
                String method,
                String url,
                JSONObject params,
                Header headers
        ) throws IllegalArgumentException {
            final RequestBody requestBody = RequestBody.create(params.toString(), Common.Network.JSON_CONTENT_TYPE);
            final Request.Builder thisRequestBuilder = (null== headers)
                    ? new Request.Builder()
                        .addHeader(Common.Network.AUTH_HEADER, Common.Network.AUTH_CONTENT+" "+headers.token)
                        .url(url)
                    : new  Request.Builder().url(url);
            switch(method.trim().toLowerCase()){
                case "get":
                    return thisRequestBuilder.get().build();
                case "delete":
                    return thisRequestBuilder.delete().build();
                case "post":
                    return thisRequestBuilder.post(requestBody).build();
                case "put":
                    return thisRequestBuilder.put(requestBody).build();
                case "patch":
                    return thisRequestBuilder.patch(requestBody).build();
            }
            return null;
        }
        private Request setFileRequest(
                String method,
                String url,
                ArrayList<FormDataRequest> params,
                ArrayList<FormDataRequest> files,
                Header headers
        ){
            final MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
            multipartBuilder.setType(MultipartBody.FORM);
            for (FormDataRequest param: params) {
                if(null!=param.data && param.data.trim().length() > 0){
                    multipartBuilder.addFormDataPart(param.property, param.data);
                }
            }
            for (FormDataRequest fileparam: files) {
                if(null!= fileparam.file && null!=fileparam.data && fileparam.data.trim().length() > 0){
                    multipartBuilder.addFormDataPart(
                            fileparam.property,
                            fileparam.data,
                            RequestBody.create(fileparam.file, Common.Network.OCTET_STREAM_TYPE)
                    );
                }
            }
            final RequestBody requestBody = multipartBuilder.build();
            final Request.Builder thisRequestBuilder = (null== headers)
                    ? new Request.Builder()
                    .addHeader(Common.Network.AUTH_HEADER, Common.Network.AUTH_CONTENT+" "+headers.token)
                    .url(url)
                    : new  Request.Builder().url(url);
            switch(method.trim().toLowerCase()){
                case "post":
                    return thisRequestBuilder.post(requestBody).build();
                case "put":
                    return thisRequestBuilder.put(requestBody).build();
                case "patch":
                    return thisRequestBuilder.patch(requestBody).build();
            }
            return null;
        }
        private void sendCall(
                OkHttpClient client,
                Request request,
                RemoteCallback callback
        ){
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    callback.onIOException(e);
                    call.cancel();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    new ResponseProcessor(call, response, callback).processResponse();
                }
            });
        }

    }
    class ResponseProcessor{
        private Call call;
        private Response response;
        private RemoteCallback callback;
        public ResponseProcessor(Call call, Response response, RemoteCallback callback){
            this.call = call;
            this.response = response;
            this.callback = callback;
        }
        public void processResponse(){
            try{
                ResponseBody responseBody = response.body();
                if(null==responseBody){
                    try{
                        JSONObject responseObject = new JSONObject();
                        responseObject.put("message","Empty response");
                        callback.onServerError(responseObject);
                        return;
                    }catch(JSONException exception){
                        callback.onJSONException(exception);
                        return;
                    }
                }
                if(response.code() >= 400 || !response.isSuccessful()){
                    try{
                        callback.onServerError(new JSONObject((responseBody.string())));
                        return;
                    }catch(JSONException exception){
                        callback.onJSONException(exception);
                        return;
                    }
                }
                Headers responseHeaders = response.headers();
                String contentType = responseHeaders.get("Content-Type").trim().toLowerCase();
                switch(contentType){
                    case "image/png":
                    case "image/jpeg":
                        byte[] pngBytes = responseBody.bytes();
                        callback.onSuccess(Converter.bytesToString(pngBytes));
                        return;
                    default:
                        callback.onSuccess(responseBody.string());
                }
            }catch(IOException ioException){
                callback.onIOException(ioException);
            }
        }
    }
    class FormDataRequest{
        private String property, data;
        private File file;
        public FormDataRequest(String property, String data){
            this.property = property;
            this.data = data;
        }
        public FormDataRequest(String property, String data, File file){
            this.property = property;
            this.data = data;
            this.file = file;
        }
    }
    class LogInterceptor implements Interceptor {
        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Log.w(SYSTAG, String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            Log.w(SYSTAG, String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 -t1) / 1e6d, response.headers()));
            return response;
        }
    }
}
