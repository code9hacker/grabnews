package com.bitfault.grabnews.common.core;

import com.bitfault.grabnews.pojo.config.Config;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Application config file. This maintains static configurations loaded on app launch
 */
@Singleton
public class AppConfig {

    public static final String CACHE_KEY = "app-config-cache-key";
    private Config config;

    @Inject
    public AppConfig() {

    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
    }
}
