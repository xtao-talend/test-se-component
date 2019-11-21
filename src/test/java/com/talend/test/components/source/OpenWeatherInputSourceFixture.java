package com.talend.test.components.source;

import com.talend.test.components.dataset.OpenWeatherDataset;
import com.talend.test.components.datastore.OpenWeatherDataStore;

import java.util.LinkedList;
import java.util.List;

class OpenWeatherInputSourceFixture {

    List<String> EXPECTED_DATA_STRING = createData();

    OpenWeatherInputMapperConfiguration VALID_WEATHER_INPUT_CONFIG = createWeatherInputConfig("Paris", "fr", "5526449918b28c796d5b1cec497d01f0");
    OpenWeatherInputMapperConfiguration WRONG_CITY_WEATHER_INPUT_CONFIG = createWeatherInputConfig("UEEU", "fr", "5526449918b28c796d5b1cec497d01f0");
    OpenWeatherInputMapperConfiguration WRONG_COUNTRY_WEATHER_INPUT_CONFIG = createWeatherInputConfig("Paris", "uk", "5526449918b28c796d5b1cec497d01f0");
    OpenWeatherInputMapperConfiguration WRONG_TOKEN_WEATHER_INPUT_CONFIG = createWeatherInputConfig("Paris", "uk", "eoaueoautoeanhntshsn");

    private List<String> createData() {
        List<String> lst = new LinkedList<>();
        lst.add("{\"city_id\":4887398,\"date\":1485703465,\"degree\":268.987}");
        lst.add("{\"city_id\":4887398,\"date\":1485730032,\"degree\":268.097}");
        lst.add("{\"city_id\":4887398,\"date\":1485755383,\"degree\":266.787}");
        lst.add("{\"city_id\":4887398,\"date\":1485780512,\"degree\":263.64}");
        return lst;
    }

    private OpenWeatherInputMapperConfiguration createWeatherInputConfig(String city, String country, String token) {
        OpenWeatherInputMapperConfiguration config = new OpenWeatherInputMapperConfiguration();

        OpenWeatherDataset dataset = new OpenWeatherDataset();
        dataset.setCity(city);
        dataset.setCountryCode(country);
        config.setDataset(dataset);

        OpenWeatherDataStore dataStore = new OpenWeatherDataStore();
        dataStore.setOpenWeatherAPIToken(token);
        dataset.setDatastore(dataStore);
        return config;
    }
}
