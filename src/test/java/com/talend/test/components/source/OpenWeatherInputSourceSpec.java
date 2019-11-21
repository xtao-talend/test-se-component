package com.talend.test.components.source;

import com.talend.test.components.dataset.OpenWeatherDataset;
import com.talend.test.components.datastore.OpenWeatherDataStore;
import org.junit.jupiter.api.*;
import org.talend.sdk.component.junit.ComponentsHandler;
import org.talend.sdk.component.junit5.Injected;
import org.talend.sdk.component.junit5.WithComponents;
import org.talend.sdk.component.runtime.input.Input;
import org.talend.sdk.component.runtime.input.Mapper;

import static org.junit.jupiter.api.Assertions.*;

@WithComponents("com.talend.test.components")
public class OpenWeatherInputSourceSpec extends OpenWeatherInputSourceFixture {
    @Injected
    private ComponentsHandler handler;

    @Test
    @DisplayName("All test records must be the same as expected data")
    public void testJsonInput() {
        OpenWeatherInputMapperConfiguration openWeatherInputMapperConfiguration = new OpenWeatherInputMapperConfiguration();

        OpenWeatherDataset dataset = new OpenWeatherDataset();
        OpenWeatherDataStore dataStore = new OpenWeatherDataStore();
        dataStore.setApplicationID("5526449918b28c796d5b1cec497d01f0");
        dataset.setCity("Paris");
        dataset.setCountryCode("fr");
        dataset.setDatastore(dataStore);
        openWeatherInputMapperConfiguration.setDataset(dataset);

        Mapper mapper = handler.createMapper(OpenWeatherInputMapper.class, openWeatherInputMapperConfiguration);

        Input input = mapper.create();
        input.start();
        Object data = null;
        int count = 0;
        while ((data = input.next()) != null) {
            count++;
        }
        assertEquals(40, count);
        input.stop();
    }

    private static void println(Object str) {
        System.out.println(str);
    }
}