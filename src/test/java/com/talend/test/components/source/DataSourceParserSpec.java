package com.talend.test.components.source;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.util.function.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceParserSpec {

    private final String testDS = "{\"message\":\"\",\"cod\":\"200\",\"city_id\":4887398,\"calctime\":0.0863,\"cnt\":4,\"list\":[{\"main\":{\"temp\":268.987,\"temp_min\":268.987,\"temp_max\":268.987,\"pressure\":1001.11,\"sea_level\":1024.68,\"grnd_level\":1001.11,\"humidity\":100},\"wind\":{\"speed\":5.06,\"deg\":291.002},\"clouds\":{\"all\":48},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"dt\":1485703465},{\"main\":{\"temp\":268.097,\"temp_min\":268.097,\"temp_max\":268.097,\"pressure\":1003.57,\"sea_level\":1027.08,\"grnd_level\":1003.57,\"humidity\":100},\"wind\":{\"speed\":8.56,\"deg\":314.007},\"clouds\":{\"all\":44},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"dt\":1485730032},{\"main\":{\"temp\":266.787,\"temp_min\":266.787,\"temp_max\":266.787,\"pressure\":1005.73,\"sea_level\":1029.63,\"grnd_level\":1005.73,\"humidity\":100},\"wind\":{\"speed\":6.79,\"deg\":316.012},\"clouds\":{\"all\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01n\"}],\"dt\":1485755383},{\"main\":{\"temp\":263.64,\"pressure\":1015,\"humidity\":57,\"temp_min\":262.15,\"temp_max\":265.15},\"wind\":{\"speed\":2.6,\"deg\":280},\"clouds\":{\"all\":1},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01n\"}],\"dt\":1485780512}]}";

    @Test
    @Tag("fast")
    void ReturnDataParser() {
        JsonValue city_id = DataSourceParser.parse(testDS, "city_id");
        JsonValue list = DataSourceParser.parse(testDS, "list");
        //getWeatherDateByCity().forEach(v -> System.out.println(v));
                //.map(v -> Tuple3.apply(v.asObject().get()));
        assertEquals(4887398, city_id.asLong(), "City id Should be 4887398");
    }
}