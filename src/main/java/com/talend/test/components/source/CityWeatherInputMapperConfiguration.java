package com.talend.test.components.source;

import java.io.Serializable;

import com.talend.test.components.dataset.WeatherDataset;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset" })
})
@Documentation("Company Processing configuration")
public class CityWeatherInputMapperConfiguration implements Serializable {
    @Option
    @Documentation("Data set")
    private WeatherDataset dataset;


    @Option
    @Documentation("Number of request")
    private String number;

    public String getNumber() {
        return number;
    }

    public WeatherDataset getDataset() {
        return dataset;
    }

    public CityWeatherInputMapperConfiguration setDataset(WeatherDataset dataset, String number) {
        this.dataset = dataset;
        this.number = number;
        return this;
    }
}