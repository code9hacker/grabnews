package com.bitfault.grabnews.pojo.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountriesConfig {

    @SerializedName("baseImgUrl")
    private String iconUrl;

    @SerializedName("list")
    private List<CountryInfoConfig> countryList;

    public String getIconUrl() {
        return iconUrl;
    }

    public List<CountryInfoConfig> getCountryList() {
        return countryList;
    }
}
