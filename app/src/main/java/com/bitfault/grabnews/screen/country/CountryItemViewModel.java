package com.bitfault.grabnews.screen.country;

import android.databinding.ObservableBoolean;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.bitfault.grabnews.R;

/**
 * ViewModel for country item used to display country selection view
 */
public class CountryItemViewModel {

    private String imageUrl;
    private String name;
    private String code;
    private InteractionListener listener;
    private ObservableBoolean showCheck = new ObservableBoolean(false);

    public CountryItemViewModel(String imageUrl, String name, String code) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.code = code;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setListener(InteractionListener listener) {
        this.listener = listener;
    }

    public InteractionListener getListener() {
        return listener;
    }

    public ObservableBoolean getShowCheck() {
        return showCheck;
    }

    public void setShowCheck(boolean showCheck) {
        this.showCheck.set(showCheck);
    }

    @DrawableRes
    public int getPlaceholder() {
        return R.drawable.ic_place;
    }

    public String getImageTag() {
        return "COUNTRY_IMAGE_TAG_" + code;
    }

    public void onItemClicked() {
        if (listener != null) {
            listener.onCountryItemClicked(this);
        }
    }

    public interface InteractionListener {
        void onCountryItemClicked(@NonNull CountryItemViewModel itemViewModel);
    }
}
