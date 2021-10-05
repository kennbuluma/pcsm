package com.foreverdevelopers.doctors_directory_kenya.util;

import static com.foreverdevelopers.doctors_directory_kenya.util.Common.SYSTAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.room.TypeConverter;

import com.foreverdevelopers.doctors_directory_kenya.data.entity.County;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Doctor;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Facility;
import com.foreverdevelopers.doctors_directory_kenya.data.entity.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Converter {

    @TypeConverter
    public static String arrayToString(List<String> input){
        if(null==input || input.size() == 0) return "";
        JSONArray array = new JSONArray();
        for(String value : input){ array.put(value); }
        return array.toString();
    }
    @TypeConverter
    public static List<String> stringToArray(String input){
        if(null==input || input.trim().length() == 0) return null;
        ArrayList<String> array = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(input);
            for (int x = 0; x < jsonArray.length(); x++) {
                array.add(jsonArray.getString(x));
            }
            return array;
        }catch (JSONException ex){
            return null;
        }
    }

    public static String bytesToString(byte[] input){
        if(null==input || input.length == 0) return null;
        return Base64.encodeToString(input, Base64.NO_WRAP);
    }
    public static byte[] stringToBytes(String input){
        if(null==input || input.trim().length() == 0) return null;
        return Base64.decode(input, Base64.DEFAULT);
    }

    public static String bitmapToString(Bitmap input){
        if(null == input) return null;
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            input.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();
            return bytesToString(bytes);
        }catch(IllegalArgumentException ex){
            return null;
        }
    }
    public static Bitmap stringToBitmap(String input){
        if(null==input || input.trim().length() == 0) return null;
        try{
            byte[] bytes = stringToBytes(input);
            if(null== bytes || bytes.length == 0) return null;
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }catch(IllegalArgumentException ex){
            return null;
        }
    }

    public static String dateToString(Date input){
        if(null==input) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ROOT);
        return sdf.format(input);
    }
    public static Date stringToDate(String input){
        if(null==input || input.trim().length() == 0) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.ROOT);
        try{
            return sdf.parse(input);
        }catch(ParseException ex){
            sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
            try{
                return sdf.parse(input);
            }catch(ParseException ey){
                return null;
            }
        }
    }

    public static String phoneToString(Integer integer){
        String val = integer.toString();
        if(val.startsWith("0") || val.startsWith("7") || val.startsWith("1")) return "+254"+val;
        return val;
    }

    public static boolean stringToBoolean(String input) throws IllegalArgumentException{
        if(null==input || input.trim().length() == 0) throw new IllegalArgumentException("Invalid boolean string");
        if("true".equalsIgnoreCase(input.trim())) return true;
        if("false".equalsIgnoreCase(input.trim())) return false;
        throw new IllegalArgumentException("Invalid boolean string");
    }

    /**
     * Doctor
     * */
    public static Doctor stringToDoctor(String input){
        if(null==input || input.trim().length() == 0) return null;
        try{
            JSONObject jsonObject = new JSONObject(input);
            return jsonToDoctor(jsonObject);
        }
        catch (JSONException ex){
            return null;
        }
    }
    public static Doctor jsonToDoctor(JSONObject input){
        if(null==input) return null;
        try{
            Doctor doctor = new Doctor();
            if(input.has("id")) doctor.id = input.getString("id");
            if(input.has("name")) doctor.name = input.getString("name");
            if(input.has("phone")) doctor.phone = input.getInt("phone");
            if(input.has("email")) doctor.email = input.getString("email");
            if(input.has("county")) doctor.county = input.getString("county");
            if(input.has("facility")) doctor.facility = input.getString("facility");
            if(input.has("specialty")) doctor.specialty = jsonList(input.getJSONArray("specialty"));
            if(input.has("profilePhoto")) doctor.profilePhoto = input.getString("profilePhoto");
            return doctor;
        }
        catch (JSONException ex){
            return null;
        }
    }

    /**
     * County
     * */
    public static County stringToCounty(String input){
        if(null==input || input.trim().length() == 0) return null;
        try{
            JSONObject jsonObject = new JSONObject(input);
            return jsonToCounty(jsonObject);
        }
        catch (JSONException ex){
            return null;
        }
    }
    public static County jsonToCounty(JSONObject input){
        if(null==input) return null;
        try{
            County county = new County();
            if(input.has("name")) county.name = input.getString("name");
            return county;
        }
        catch (JSONException ex){
            return null;
        }
    }
    /**
     * Facility
     * */
    public static Facility stringToFacility(String input){
        if(null==input || input.trim().length() == 0) return null;
        try{
            JSONObject jsonObject = new JSONObject(input);
            return jsonToFacility(jsonObject);
        }
        catch (JSONException ex){
            return null;
        }
    }
    public static Facility jsonToFacility(JSONObject input){
        if(null==input) return null;
        try{
            Facility facility = new Facility();
            if(input.has("name")) facility.name = input.getString("name");
            return facility;
        }
        catch (JSONException ex){
            return null;
        }
    }
    /**
     * Service
     * */
    public static Service stringToService(String input){
        if(null==input || input.trim().length() == 0) return null;
        try{
            JSONObject jsonObject = new JSONObject(input);
            return jsonToService(jsonObject);
        }
        catch (JSONException ex){
            return null;
        }
    }
    public static Service jsonToService(JSONObject input){
        if(null==input) return null;
        try{
            Service service = new Service();
            if(input.has("name")) service.name = input.getString("name");
            return service;
        }
        catch (JSONException ex){
            return null;
        }
    }

    /**
     * Others
     * */
    public static JSONObject objectToJSON(Object input){
        if(null==input) return null;
        try{
            JSONObject jsonObject = new JSONObject();
            if(input instanceof County) jsonObject.put("name", ((County) input).name);
            if(input instanceof Service) jsonObject.put("name", ((Service) input).name);
            if(input instanceof Facility) jsonObject.put("name", ((Facility) input).name);
            if(input instanceof Doctor){
                Doctor doctor = (Doctor) input;
                jsonObject.put("id", doctor.id);
                jsonObject.put("name", doctor.name);
                jsonObject.put("phone", doctor.phone);
                jsonObject.put("email", doctor.email);
                jsonObject.put("county", doctor.county);
                jsonObject.put("facility", doctor.facility);
                jsonObject.put("specialty", doctor.specialty);
                jsonObject.put("profilePhoto", doctor.profilePhoto);
            }
            return jsonObject;
        }catch (JSONException ex){
            return null;
        }
    }
    public static String objectToString(Object input){
        return objectToJSON(input).toString();
    }

    private static List<String> jsonList(JSONArray input){
        if(null == input || input.length() == 0) return null;
        ArrayList<String> retVal = new ArrayList<>();
        for(int x = 0; x < input.length(); x++){
            try{
                retVal.add(input.getString(x));}
            catch (JSONException ex){
                Log.e(SYSTAG, "Invalid value");
            }
        }
        return retVal;
    }
}
