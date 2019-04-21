package com.bitfault.grabnews.pojo.config;

import com.google.gson.annotations.SerializedName;

public class CountryInfoConfig {

    @SerializedName("code")
    private String code;

    @SerializedName("name")
    private String displayName;

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
