package com.talend.test.components.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.talend.test.components.datastore.OpenWeatherDataStore;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;
import reactor.util.function.*;


@Documentation("OpenWeather Input Source")
public class OpenWeatherInputSource implements Serializable {

    private final OpenWeatherInputMapperConfiguration configuration;

    private final RecordBuilderFactory builderFactory;

    private Iterator<Record> iterator;

    private final String OPEN_WEATHER_HOST = "http://api.openweathermap.org/data/2.5";

    public OpenWeatherInputSource(@Option("configuration") final OpenWeatherInputMapperConfiguration configuration,
                                  final RecordBuilderFactory builderFactory) {
        this.configuration = configuration;
        this.builderFactory = builderFactory;
    }

    @PostConstruct
    public void init() {
        // this method will be executed once for the whole component execution,
        // this is where you can establish a connection for instance

        // String url = "api.openweathermap.org/data/2.5/forecast?q=Paris,fr&APPID=5526449918b28c796d5b1cec497d01f0";
       OpenWeatherRequest();
    }

    private void OpenWeatherRequest() {
        String city = configuration.getDataset().getCity();
        String country = configuration.getDataset().getCountryCode();
        String url = constructOpenWeatherURL(city, country);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        RuntimeException re = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            if (statusCode == 404 || statusCode == 401) {
                re = new RuntimeException(message);
                ArrayList<Record> record = new ArrayList<>();
                Record errorRecord = builderFactory
                        .newRecordBuilder()
                        .withString("ErrorMessage", message)
                        .build();
                record.add(errorRecord);
                iterator = record.iterator();
            } else if (statusCode == 200) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));

                String jsonData = "";
                String line;
                while ((line = rd.readLine()) != null) {
                    jsonData += line;
                }
                initDataSet(jsonData, city, country);
            }
            response.close();
        } catch (Exception e) {
            throw re != null ? re : new RuntimeException(e.getMessage());
        }
    }

    private String constructOpenWeatherURL(String cityName, String countryCode) {
        OpenWeatherDataStore datastore = configuration.getDataset().getDatastore();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder
                    .append(OPEN_WEATHER_HOST)
                    .append("/forecast?q=")
                    .append(cityName)
                    .append(",")
                    .append(countryCode)
                    .append("&APPID=")
                    .append(datastore.getOpenWeatherAPIToken());
            return stringBuilder.toString();
    }


    /*private <T1, T2> Function<Optional<T1>, Optional<T2>> Option(Optional<T1> optionOne, Optional<T2> optionTwo) {
        optionOne.
    }*/

    private void initDataSet(String jsonData, String city, String country) {
        List<Tuple4<String, String, Date, Double>> weatherDateByCity = getWeatherDateByCity(jsonData, city, country);
        iterator = weatherDateByCity
                .stream()
                .map(weather -> createWeatherRecord(weather, builderFactory.newRecordBuilder()))
                .collect(Collectors.toList())
                .iterator();
    }

    @Producer
    public Record next() {
        // this is the method allowing you to go through the dataset associated
        // to the component configuration
        //
        // return null means the dataset has no more data to go through
        // you can use the builderFactory to create a new Record.
        return iterator.hasNext() ? iterator.next() : null;
    }

    private Record createWeatherRecord(Tuple4<String, String, Date, Double> weather, Record.Builder recordBuilder) {
        recordBuilder.withString("city", weather.getT2());
        recordBuilder.withString("countryCode", weather.getT1());
        recordBuilder.withDateTime("date", weather.getT3());
        recordBuilder.withDouble("degree(°C)", weather.getT4());
        return recordBuilder.build();
    }

    private List<Tuple4<String, String, Date, Double>> getWeatherDateByCity(String jsonData, String city, String country) {
        JsonValue weatherList = DataSourceParser.parse(jsonData, "list");

        return weatherList
                .asArray()
                .values()
                .stream()
                .map(weather -> getWeatherByDate(weather, city, country))
                .collect(Collectors.toList());
    }

    private Tuple4<String, String, Date, Double> getWeatherByDate(JsonValue weatherInformation, String city, String country) {
        JsonObject weather = weatherInformation.asObject();
        JsonObject mainFields = weather.get("main").asObject();
        double degree = mainFields.get("temp").asDouble() - 273.15;
        Date dateTime = DataSourceParser.StringToDate(weather.get("dt_txt").asString());
        return Tuples.of(country, city, dateTime, degree);
    }

    @PreDestroy
    public void release() {
        // this is the symmetric method of the init() one,
        // release potential connections you created or data you cached
    }

    private void println(Object str) {
        System.out.println(str);
    }
}