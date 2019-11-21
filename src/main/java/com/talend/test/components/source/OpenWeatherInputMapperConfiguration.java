package com.talend.test.components.source;

import java.io.Serializable;

import com.talend.test.components.dataset.OpenWeatherDataset;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "dataset"})
})
@Documentation("OpenWeather Input configuration")
public class OpenWeatherInputMapperConfiguration implements Serializable {
    @Option
    @Documentation("OpenWeather DataSet")
    private OpenWeatherDataset dataset;

    public OpenWeatherDataset getDataset() {
        return dataset;
    }

    public OpenWeatherInputMapperConfiguration setDataset(OpenWeatherDataset dataset) {
        this.dataset = dataset;
        return this;
    }
}