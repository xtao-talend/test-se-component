package com.talend.test.components.source;

import com.talend.test.components.dataset.OpenWeatherDataset;
import com.talend.test.components.datastore.OpenWeatherDataStore;
import org.hamcrest.CoreMatchers.*;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.AnyOf;
import org.hamcrest.core.Is;
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
    @DisplayName("All test records must be the same as expected data when given correct city, country and token")
    public void validRequestJsonInput() {
        Mapper mapper = handler.createMapper(OpenWeatherInputMapper.class, VALID_WEATHER_INPUT_CONFIG);

        Input input = mapper.create();
        input.start();
        Object data = input.next();
        int count = 0;
        while (data != null) {
            count++;
            data = input.next();
        }
        assertEquals(40, count);
        MatcherAssert.assertThat(count, AnyOf.anyOf(Is.is(1), Is.is(40)));
        input.stop();
    }

    @Test
    @DisplayName("Return a record when given wrong city")
    public void invalidCityJsonInput() {
        Mapper mapper = handler.createMapper(OpenWeatherInputMapper.class, WRONG_CITY_WEATHER_INPUT_CONFIG);

        Input input = mapper.create();
        input.start();
        Object data = input.next();
        int count = 0;
        while (data != null) {
            count++;
            data = input.next();
        }
        assertEquals(1, count);
        // MatcherAssert.assertThat(count, AnyOf.anyOf(Is.is(1), Is.is(40)));
        input.stop();
    }

    @Test
    @DisplayName("Return a record when given wrong country")
    public void invalidCountryJsonInput() {
        Mapper mapper = handler.createMapper(OpenWeatherInputMapper.class, WRONG_COUNTRY_WEATHER_INPUT_CONFIG);

        Input input = mapper.create();
        input.start();
        Object data = input.next();
        int count = 0;
        while (data != null) {
            count++;
            data = input.next();
        }
        assertEquals(1, count);
        // MatcherAssert.assertThat(count, AnyOf.anyOf(Is.is(1), Is.is(40)));
        input.stop();
    }

    @Test
    @DisplayName("Return a record when given wrong token")
    public void testJsonInput() {
        Mapper mapper = handler.createMapper(OpenWeatherInputMapper.class, WRONG_TOKEN_WEATHER_INPUT_CONFIG);

        Input input = mapper.create();
        input.start();
        Object data = input.next();
        int count = 0;
        while (data != null) {
            count++;
            data = input.next();
        }
        assertEquals(1, count);
        // MatcherAssert.assertThat(count, AnyOf.anyOf(Is.is(1), Is.is(40)));
        input.stop();
    }

    private static void println(Object str) {
        System.out.println(str);
    }
}