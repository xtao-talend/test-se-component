package com.talend.test.components.source;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class DataSourceParser {

    public static JsonValue parse(String jsonString, String fieldName) {
        JsonObject jsonObj = Json.parse(jsonString).asObject();
        JsonValue jsonValue = jsonObj.get(fieldName);
        return jsonValue;
    }

}
