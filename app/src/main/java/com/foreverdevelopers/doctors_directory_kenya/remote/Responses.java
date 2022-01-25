package com.foreverdevelopers.doctors_directory_kenya.remote;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.util.Log;

import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Responses {
    public void counties(){}
    public void facilities(){}
    public void services(){}
    public void doctors(){}

    public static class MainResponse{
        public Integer code;
        public String message, status;
        public JSONArray data;
        public MainResponse(){}
        public MainResponse(Integer code,
                            String message,
                            String status,
                            JSONArray data){
            this.code = code;
            this.message = message;
            this.status = status;
            this.data = data;
        }
    }
    public static class MainResponseObject{
        public Integer code;
        public String message, status;
        public JSONObject data;
        public MainResponseObject(){}
        public MainResponseObject(Integer code,
                            String message,
                            String status,
                            JSONObject data){
            this.code = code;
            this.message = message;
            this.status = status;
            this.data = data;
        }
    }

    public static MainResponse mainResponse(String result){
        try{
            JSONObject responseObject = new JSONObject(result);
            MainResponse response = new MainResponse();
            if ((responseObject.has("code"))) {
                response.code = responseObject.getInt("code");
            }
            if ((responseObject.has("message"))) {
                response.message = responseObject.getString("message");
            }
            if ((responseObject.has("status"))) {
                response.status = responseObject.getString("status");
            }
            if ((responseObject.has("data"))) {
                response.data = responseObject.getJSONArray("data");
            }
            return response;
        }catch (JSONException ex){
            Log.e(SYSTAG, ex.getLocalizedMessage());
            return null;
        }

    }
    public static MainResponseObject mainResponseObject(String result){
        try{
            JSONObject responseObject = new JSONObject(result);
            MainResponseObject response = new MainResponseObject();
            if ((responseObject.has("code"))) {
                response.code = responseObject.getInt("code");
            }
            if ((responseObject.has("message"))) {
                response.message = responseObject.getString("message");
            }
            if ((responseObject.has("status"))) {
                response.status = responseObject.getString("status");
            }
            if ((responseObject.has("data"))) {
                response.data = responseObject.getJSONObject("data");
            }
            return response;
        }catch (JSONException ex){
            Log.e(SYSTAG, ex.getLocalizedMessage());
            return null;
        }

    }
    public static Doctor doctorsResponse(JSONObject result){
        if(null== result) return null;
        try{
            Doctor doctor = new Doctor();
            doctor.phone = result.has("phone") ? result.getInt("phone") : 0;
            doctor.specialty = result.has("specialty") ? doctorSpecialties(result.getJSONArray("specialty")) : null;
            doctor.id = result.has("id") ? result.getString("id") : "";
            doctor.name = result.has("name") ? result.getString("name") : "";
            doctor.county = result.has("county") ? result.getString("county") : "";
            doctor.profilePhoto = result.has("profilePhoto") ?  result.getString("profilePhoto") : "";
            doctor.facility = result.has("facility") ? result.getString("facility") : "";
            doctor.email = result.has("email") ? result.getString("email") : "";
            return doctor;
        }catch(JSONException ex){
            return null;
        }
    }
    private static ArrayList<String> doctorSpecialties(JSONArray result){
        if(null==result || result.length() == 0 ) return null;
        try{
            ArrayList<String> dspecialty = new ArrayList<>();
            for(int i = 0; i < result.length(); i ++){
                dspecialty.add(result.getString(i));
            }
            return  dspecialty;
        }catch(JSONException ex){
            Log.e(SYSTAG, ex.getLocalizedMessage());
            return null;
        }
    }
}
