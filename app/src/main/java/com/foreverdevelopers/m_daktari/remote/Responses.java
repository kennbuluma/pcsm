package com.foreverdevelopers.m_daktari.remote;

import static com.foreverdevelopers.m_daktari.util.Common.SYSTAG;

import android.util.Log;

import com.foreverdevelopers.m_daktari.data.entity.Doctor;

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
    public static Doctor doctorsResponse(JSONObject result){
        if(null== result) return null;
        try{
            Doctor doctor = new Doctor();
            if(result.has("phone")){
                doctor.phone = result.getInt("phone");
            }
            if(result.has("specialty")){
                doctor.specialty = doctorSpecialties(result.getJSONArray("specialty"));
            }
            if(result.has("id")){
                doctor.id = result.getString("id");
            }
            if(result.has("name")){
                doctor.name = result.getString("name");
            }
            if(result.has("county")){
                doctor.county = result.getString("county");
            }
            if(result.has("profilePhoto")){
                doctor.profilePhoto = result.getString("profilePhoto");
            }
            if(result.has("facility")){
                doctor.facility = result.getString("facility");
            }
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
