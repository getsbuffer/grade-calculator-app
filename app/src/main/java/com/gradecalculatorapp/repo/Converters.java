package com.gradecalculatorapp.repo;

import android.util.Log;

import androidx.room.TypeConverter;

import com.gradecalculatorapp.model.GradeDetail;

import java.util.HashMap;
import java.util.Map;

public class Converters {

    @TypeConverter
    public static Map<String, GradeDetail> fromString(String value) {
        if (value == null || value.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, GradeDetail> map = new HashMap<>();
        String[] entries = value.split(",");
        for (String entry : entries) {
            String[] keyValue = entry.split("=");
            if (keyValue.length == 2) { // Ensure the entry contains exactly two parts
                String category = keyValue[0];
                String[] detailParts = keyValue[1].split(":");
                if (detailParts.length == 2) {
                    try {
                        double gradeValue = Double.parseDouble(detailParts[0]);
                        double weightValue = Double.parseDouble(detailParts[1]);
                        map.put(category, new GradeDetail(gradeValue, weightValue));
                    } catch (NumberFormatException e) {
                        Log.e("Converters", "Error parsing number in fromString: " + entry, e);
                    }
                } else {
                    Log.e("Converters", "Unexpected format for GradeDetail in fromString: " + entry);
                }
            } else {
                Log.e("Converters", "Unexpected format in fromString: " + entry);
            }
        }
        return map;
    }

    @TypeConverter
    public static String fromMap(Map<String, GradeDetail> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, GradeDetail> entry : map.entrySet()) {
            GradeDetail detail = entry.getValue();
            sb.append(entry.getKey())
                    .append("=")
                    .append(detail.getValue())
                    .append(":")
                    .append(detail.getWeight())
                    .append(",");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
