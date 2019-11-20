package com.talend.test.components.dataset;

import java.io.Serializable;

import com.talend.test.components.datastore.WeatherDataStore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@DataSet("WeatherDataSet")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "datastore" }),
    @GridLayout.Row({ "format" }),
    @GridLayout.Row({ "name" })
})
@Documentation("Test component data set attributes")
public class WeatherDataset implements Serializable {
    @Option
    @Documentation("Custom data store")
    private WeatherDataStore datastore;

    @Option
    @Documentation("Data format like json, csv...")
    private String format;

    @Option
    @Documentation("Data set name")
    private String name;

    public WeatherDataStore getDatastore() {
        return datastore;
    }

    public WeatherDataset setDatastore(WeatherDataStore datastore) {
        this.datastore = datastore;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public WeatherDataset setFormat(String format) {
        this.format = format;
        return this;
    }

    public String getName() {
        return name;
    }

    public WeatherDataset setName(String name) {
        this.name = name;
        return this;
    }
}