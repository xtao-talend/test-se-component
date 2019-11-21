package com.talend.test.components.source;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataSourceParser {

    public static JsonValue parse(String jsonString, String fieldName) {
        JsonObject jsonObj = Json.parse(jsonString).asObject();
        JsonValue jsonValue = jsonObj.get(fieldName);
        return jsonValue;
    }

    public static Date StringToDate(String dateString) {
        try {
            return YYYY_MM_DD_HH_mm_ss.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SimpleDateFormat YYYY_MM_DD_HH_mm_ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
