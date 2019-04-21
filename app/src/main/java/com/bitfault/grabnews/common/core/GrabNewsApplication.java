package com.bitfault.grabnews.common.core;

import android.app.Application;
import android.util.Log;

import com.bitfault.grabnews.common.di.ApplicationComponent;
import com.bitfault.grabnews.common.di.ApplicationModule;
import com.bitfault.grabnews.common.di.DaggerApplicationComponent;
import com.bitfault.grabnews.common.network.ResponseCacheManager;
import com.bitfault.grabnews.pojo.config.Config;

import javax.inject.Inject;

import io.reactivex.plugins.RxJavaPlugins;

public class GrabNewsApplication extends Application {

    private static final String LOG_TAG = "GrabNewsApplication";
    public static GrabNewsApplication mContext;
    public static ApplicationComponent applicationComponent;

    @Inject
    AppConfig appConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = GrabNewsApplication.this;
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule()).build();
        applicationComponent.inject(this);
        setUpApplication();
    }

    private void setUpApplication() {
        fetchConfig();
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.e(LOG_TAG, throwable.getMessage());
        });
    }

    /**
     * Initializes app config using previously saved data
     */
    private void fetchConfig() {
        Config config = ResponseCacheManager.getInstance().fetchResponse(AppConfig.CACHE_KEY, Config.class);
        if (config != null) {
            appConfig.setConfig(config);
        }
    }

}
