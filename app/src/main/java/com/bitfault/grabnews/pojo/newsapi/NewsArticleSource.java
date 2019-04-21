package com.bitfault.grabnews.pojo.newsapi;

import com.google.gson.annotations.SerializedName;

public class NewsArticleSource {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
