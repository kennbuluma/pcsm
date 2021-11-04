package com.foreverdevelopers.doctors_directory_kenya.remote;

import com.foreverdevelopers.doctors_directory_kenya.util.Common;

import org.json.JSONException;
import org.json.JSONObject;

public class Requests {
    public static JSONObject doctorDetail(String id){
        if(!Common.sanitizeString(id)) return null;
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",id);
            return jsonObject;
        }catch (JSONException ex){
            return null;
        }
    }
}
