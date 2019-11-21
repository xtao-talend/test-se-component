package com.talend.test.components.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.talend.sdk.component.api.base.BufferizedProducerSupport;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;
import com.talend.test.components.service.TalendTestComponentService;
import reactor.util.function.*;


@Documentation("Read the data from a custom data store")
public class CityWeatherInputSource implements Serializable {
    private final CityWeatherInputMapperConfiguration configuration;
    private final TalendTestComponentService service;
    private final RecordBuilderFactory builderFactory;
    private BufferizedProducerSupport<Record> bufferedReader;

    private final String testDS = "{\"message\":\"\",\"cod\":\"200\",\"city_id\":4887398,\"calctime\":0.0863,\"cnt\":4,\"list\":[{\"main\":{\"temp\":268.987,\"temp_min\":268.987,\"temp_max\":268.987,\"pressure\":1001.11,\"sea_level\":1024.68,\"grnd_level\":1001.11,\"humidity\":100},\"wind\":{\"speed\":5.06,\"deg\":291.002},\"clouds\":{\"all\":48},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"dt\":1485703465},{\"main\":{\"temp\":268.097,\"temp_min\":268.097,\"temp_max\":268.097,\"pressure\":1003.57,\"sea_level\":1027.08,\"grnd_level\":1003.57,\"humidity\":100},\"wind\":{\"speed\":8.56,\"deg\":314.007},\"clouds\":{\"all\":44},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"dt\":1485730032},{\"main\":{\"temp\":266.787,\"temp_min\":266.787,\"temp_max\":266.787,\"pressure\":1005.73,\"sea_level\":1029.63,\"grnd_level\":1005.73,\"humidity\":100},\"wind\":{\"speed\":6.79,\"deg\":316.012},\"clouds\":{\"all\":0},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01n\"}],\"dt\":1485755383},{\"main\":{\"temp\":263.64,\"pressure\":1015,\"humidity\":57,\"temp_min\":262.15,\"temp_max\":265.15},\"wind\":{\"speed\":2.6,\"deg\":280},\"clouds\":{\"all\":1},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"sky is clear\",\"icon\":\"01n\"}],\"dt\":1485780512}]}";

    public CityWeatherInputSource(@Option("configuration") final CityWeatherInputMapperConfiguration configuration,
                                  final TalendTestComponentService service,
                                  final RecordBuilderFactory builderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.builderFactory = builderFactory;
    }

    @PostConstruct
    public void init() {
        // this method will be executed once for the whole component execution,
        // this is where you can establish a connection for instance
        //configuration.getDataset().getFormat();
        initDataSet(testDS);
    }

    private void RequestDataByAPI() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://samples.openweathermap.org/data/2.5/history/city?lat=41.85&lon=-87.65&appid=b1b15e88fa797225412429c1c50c122a1");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            println(response.getStatusLine());
            //HttpEntity entity1 = response.getEntity();
            //EntityUtils.consume(entity1);
            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(
                            response.getEntity().getContent()));

            String jsonData = "";
            String line = "";
            while ((line = rd.readLine()) != null) {
                println(line);
                jsonData += line;
            }
            initDataSet(jsonData);
            response.close();
        } catch (Exception e) {

        }
    }

    /*private <T1, T2> Function<Optional<T1>, Optional<T2>> Option(Optional<T1> optionOne, Optional<T2> optionTwo) {
        optionOne.
    }*/

    private void initDataSet(String jsonData) {
        List<Tuple3<Long, Long, Double>> weatherDateByCity = getWeatherDateByCity(jsonData);

        Stream<Record> records = weatherDateByCity
                .stream()
                .map(weather -> createWeatherRecord(weather, builderFactory.newRecordBuilder()));

        bufferedReader = new BufferizedProducerSupport<>(() -> {

                return records.iterator();

        });
    }

    @Producer
    public Record next() {
        // this is the method allowing you to go through the dataset associated
        // to the component configuration
        //
        // return null means the dataset has no more data to go through
        // you can use the builderFactory to create a new Record.
        Record record = bufferedReader.next();
        return record != null ? record : null;
    }

    private Record createWeatherRecord(Tuple3<Long, Long, Double> weather, Record.Builder recordBuilder) {
        recordBuilder.withLong("city_id", weather.getT1());
        recordBuilder.withLong("date", weather.getT2());
        recordBuilder.withDouble("degree", weather.getT3());
        return recordBuilder.build();
    }

    private List<Tuple3<Long, Long, Double>> getWeatherDateByCity(String jsonData) {
        JsonValue weatherList = DataSourceParser.parse(jsonData, "list");
        JsonValue cityId = DataSourceParser.parse(jsonData, "city_id");
        
        return weatherList
                .asArray()
                .values()
                .stream()
                .map(weather -> getWeatherByDate(weather, cityId))
                .collect(Collectors.toList());
    }

    private Tuple3<Long, Long, Double> getWeatherByDate(JsonValue weatherInformation, JsonValue cityId) {
        JsonObject weather = weatherInformation.asObject();
        JsonObject mainFields = weather.get("main").asObject();
        long city = cityId.asLong();
        double degree = mainFields.get("temp").asDouble();
        long dateTime = weather.get("dt").asLong();
        return Tuples.of(city, dateTime, degree);
    }

    @PreDestroy
    public void release() {
        // this is the symmetric method of the init() one,
        // release potential connections you created or data you cached
    }

    private static void println(Object str) {
        System.out.println(str);
    }
}