package com.bitfault.grabnews.screen.splash;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.Bundle;

import com.bitfault.grabnews.R;
import com.bitfault.grabnews.common.core.AppConfig;
import com.bitfault.grabnews.common.core.CommonApiManager;
import com.bitfault.grabnews.common.core.GrabNewsApplication;
import com.bitfault.grabnews.common.model.event.AddFragmentEvent;
import com.bitfault.grabnews.common.model.event.BaseEvent;
import com.bitfault.grabnews.common.model.event.LaunchActivityEvent;
import com.bitfault.grabnews.common.storage.SharedPreferenceHelper;
import com.bitfault.grabnews.common.util.AppConstants;
import com.bitfault.grabnews.common.util.StringUtils;
import com.bitfault.grabnews.screen.country.CountrySelectionFragment;
import com.bitfault.grabnews.screen.home.HomeActivity;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SplashActivityViewModel extends ViewModel implements LifecycleObserver {

    private static final String LOG_TAG = "SplashActivityViewModel";
    public ObservableField<String> splashText = new ObservableField<>("Setting up application");
    public ObservableBoolean splashNoInternet = new ObservableBoolean(false);
    public ObservableBoolean splashProgressBar = new ObservableBoolean(true);

    private MutableLiveData<BaseEvent> eventLiveData = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    AppConfig appConfig;
    @Inject
    CommonApiManager apiManager;
    @Inject
    SharedPreferenceHelper sharedPreferenceHelper;

    public SplashActivityViewModel() {
        GrabNewsApplication.applicationComponent.inject(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        //ActivityComponent activityComponent =
        performStep1();
    }

    private void performStep1() {
        // app config init
        apiManager.fetchAppConfig()
                .doOnSubscribe(compositeDisposable::add)
                .subscribe(config -> {
                    appConfig.setConfig(config);
                    performStep2();
                }, e -> {
                    if (appConfig.getConfig() != null) {
                        performStep2();
                    } else {
                        splashText.set("There was some problem in loading appplication. Please check your internet");
                        splashNoInternet.set(true);
                        splashProgressBar.set(false);
                    }
                });
    }

    private void performStep2() {
        // country selection init
        if (!shouldLaunchCountrySelection()) {
            performStep3(getSavedCountryCode());
            return;
        }
        CountrySelectionFragment fragment = new CountrySelectionFragment();
        AddFragmentEvent addFragmentEvent = new AddFragmentEvent(fragment, R.id.main_fragment_container);
        addFragmentEvent.setAddToBackStack(false);
        eventLiveData.setValue(addFragmentEvent);
    }

    private void performStep3(String countryCode) {
        // launch top headlines screen
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.BUNDLE_KEY_COUNTRY_CODE, countryCode);
        LaunchActivityEvent launchActivityEvent = new LaunchActivityEvent(HomeActivity.class, bundle);
        eventLiveData.setValue(launchActivityEvent);
    }

    private boolean shouldLaunchCountrySelection() {
        return StringUtils.isNullEmpty(getSavedCountryCode());
    }

    private String getSavedCountryCode() {
        return sharedPreferenceHelper.getString(SharedPreferenceHelper.KEY_COUNTRY_CODE);
    }

    public void onCountrySelected(String code) {
        performStep3(code);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        compositeDisposable.clear();
    }

    public LiveData<BaseEvent> getEventLiveData() {
        return eventLiveData;
    }
}
