package com.talend.test.components.datastore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("WeatherDataStore")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "URL" })
})
@Documentation("Custom data store, here we get our data store from a REST api URL")
public class WeatherDataStore implements Serializable {
    @Option
    @Documentation("URL for getting the data source")
    private String URL;

    public String getURL() {
        return URL;
    }

    public WeatherDataStore setURL(String URL) {
        this.URL = URL;
        return this;
    }
}