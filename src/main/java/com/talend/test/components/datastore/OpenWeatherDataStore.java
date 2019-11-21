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
    @GridLayout.Row({ "ApplicationID" })
})
@Documentation("OpenWeather DataStore")
public class OpenWeatherDataStore implements Serializable {
    @Option
    @Required
    @Documentation("Application ID to request OpenWeather")
    private String ApplicationID;

    public String getApplicationID() {
        return ApplicationID;
    }

    public OpenWeatherDataStore setApplicationID(String applicationID) {
        this.ApplicationID = applicationID;
        return this;
    }
}