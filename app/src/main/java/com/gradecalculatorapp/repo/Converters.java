package com.gradecalculatorapp.repo;

import android.util.Log;

import androidx.room.TypeConverter;

import java.util.HashMap;
import java.util.Map;

public class Converters {

    @TypeConverter
    public static Map<String, Double> fromString(String value) {
        if (value == null || value.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Double> map = new HashMap<>();
        String[] entries = value.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            if (keyValue.length == 2) { // Ensure that the entry contains exactly two parts
                map.put(keyValue[0], Double.parseDouble(keyValue[1]));
            } else {
                // Handle unexpected format or log an error
                Log.e("Converters", "Unexpected format in fromString: " + entry);
            }
        }
        return map;
    }

    @TypeConverter
    public static String fromMap(Map<String, Double> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        return sb.toString();
    }
}
