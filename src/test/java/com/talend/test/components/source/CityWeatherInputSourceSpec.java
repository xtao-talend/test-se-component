package com.talend.test.components.source;

import org.junit.jupiter.api.*;
import org.talend.sdk.component.junit.ComponentsHandler;
import org.talend.sdk.component.junit5.Injected;
import org.talend.sdk.component.junit5.WithComponents;
import org.talend.sdk.component.runtime.input.Input;
import org.talend.sdk.component.runtime.input.Mapper;

import static org.junit.jupiter.api.Assertions.*;

@WithComponents("com.talend.test.components")
public class CityWeatherInputSourceSpec extends CompanyInputSourceFixture {
    @Injected
    private ComponentsHandler handler;

    @Test
    @DisplayName("All test records must be the same as expected data")
    public void testJsonInput() {
        CityWeatherInputMapperConfiguration cityWeatherInputMapperConfiguration = new CityWeatherInputMapperConfiguration();

        Mapper mapper = handler.createMapper(CityWeatherInputMapper.class, cityWeatherInputMapperConfiguration);

        assertFalse(mapper.isStream());

        Input input = mapper.create();
        input.start();
        EXPECTED_DATA_STRING
                .forEach(data -> assertEquals(data, input.next().toString()));
        input.stop();
    }

    private static void println(Object str) {
        System.out.println(str);
    }
}