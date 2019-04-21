package com.bitfault.grabnews.pojo.config;

import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("countries")
    private CountriesConfig countries;

    public CountriesConfig getCountries() {
        return countries;
    }
}
