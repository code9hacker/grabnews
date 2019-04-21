package com.bitfault.grabnews.screen.topHeadlines;

import android.support.annotation.NonNull;

public class NewsItemViewModel {

    private String sourceIcon;
    private String sourceText;
    private String title;
    private String imageUrl;
    private String url;
    private InteractionListener listener;
    private String actualSource;

    public String getSourceIcon() {
        return sourceIcon;
    }

    public void setSourceIcon(String sourceIcon) {
        this.sourceIcon = sourceIcon;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setListener(InteractionListener listener) {
        this.listener = listener;
    }

    public void setActualSource(String actualSource) {
        this.actualSource = actualSource;
    }

    public String getActualSource() {
        return actualSource;
    }

    public void onItemClicked() {
        if (listener != null) {
            listener.onNewsItemClicked(this);
        }
    }

    public interface InteractionListener {
        void onNewsItemClicked(@NonNull NewsItemViewModel model);
    }
}
