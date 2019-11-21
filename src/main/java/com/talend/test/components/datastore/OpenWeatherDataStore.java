package com.talend.test.components.datastore;

import java.io.Serializable;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataStore;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@DataStore("OpenWeatherDataStore")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "OpenWeatherAPIToken" })
})
@Documentation("OpenWeather DataStore")
public class OpenWeatherDataStore implements Serializable {

    @Option
    @Required
    @Documentation("OpenWeatherAPIToken")
    private String OpenWeatherAPIToken;

    public String getOpenWeatherAPIToken() {
        return OpenWeatherAPIToken;
    }

    public OpenWeatherDataStore setOpenWeatherAPIToken(String openWeatherAPIToken) {
        this.OpenWeatherAPIToken = openWeatherAPIToken;
        return this;
    }
}