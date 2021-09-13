package com.foreverdevelopers.doctors_directory_kenya.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
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

    public static boolean stringToBoolean(String input) throws IllegalArgumentException{
        if(null==input || input.trim().length() == 0) throw new IllegalArgumentException("Invalid boolean string");
        if("true".equalsIgnoreCase(input.trim())) return true;
        if("false".equalsIgnoreCase(input.trim())) return false;
        throw new IllegalArgumentException("Invalid boolean string");
    }
}
