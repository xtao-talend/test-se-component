package com.talend.test.components.dataset;

import java.io.Serializable;

import com.talend.test.components.datastore.OpenWeatherDataStore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.constraint.Required;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@DataSet("OpenWeatherDataSet")
@GridLayout({
    // the generated layout put one configuration entry per line,
    // customize it as much as needed
    @GridLayout.Row({ "datastore" }),
    @GridLayout.Row({ "city" }),
    @GridLayout.Row({ "countryCode" })
})
@Documentation("Test component data set attributes")
public class OpenWeatherDataset implements Serializable {
    @Option
    @Required
    @Documentation("OpenWeather DataStore")
    private OpenWeatherDataStore datastore;

    @Option
    @Required
    @Documentation("City Name")
    private String city;

    public String getCountryCode() {
        return countryCode;
    }

    public OpenWeatherDataset setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    @Option
    @Documentation("Country Code")
    private String countryCode;

    public OpenWeatherDataStore getDatastore() {
        return datastore;
    }

    public OpenWeatherDataset setDatastore(OpenWeatherDataStore datastore) {
        this.datastore = datastore;
        return this;
    }

    public String getCity() {
        return city;
    }

    public OpenWeatherDataset setCity(String city) {
        this.city = city;
        return this;
    }
}