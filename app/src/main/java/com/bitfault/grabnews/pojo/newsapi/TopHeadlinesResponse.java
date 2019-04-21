package com.bitfault.grabnews.pojo.newsapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopHeadlinesResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private String errorCode;

    @SerializedName("message")
    private String errorMessage;

    @SerializedName("totalResults")
    private int totalResults;

    @SerializedName("articles")
    private List<NewsArticle> articleList;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<NewsArticle> getArticleList() {
        return articleList;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
