package com.bitfault.grabnews.common.core;

import com.bitfault.grabnews.common.network.BaseRequest;
import com.bitfault.grabnews.common.network.HttpMethod;
import com.bitfault.grabnews.common.network.NetworkManager;
import com.bitfault.grabnews.common.network.ResponseCacheManager;
import com.bitfault.grabnews.common.util.AppConstants;
import com.bitfault.grabnews.pojo.config.Config;
import com.bitfault.grabnews.pojo.newsapi.TopHeadlinesResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * A repository class managing all api calls.
 */
@Singleton
public class CommonApiManager {

    @Inject
    NetworkManager networkManager;

    @Inject
    public CommonApiManager() {
        GrabNewsApplication.applicationComponent.inject(this);
    }

    public static final String CONFIG_API_TAG = "api-tag-config";
    private static final String CONFIG_URL = "https://grabnews-4e509.firebaseio.com/config.json";
    public static final String TOP_HEADLINES_API_TAG = "api-tag-top-headlines";
    private static final String TOP_HEADLINES_URL = "https://newsapi.org/v2/top-headlines?country=%1$s&apiKey="
            + AppConstants.NEWS_API_AUTH_KEY;

    public Observable<Config> fetchAppConfig() {
        BaseRequest request = new BaseRequest.Builder(CONFIG_URL, CONFIG_API_TAG).build();
        return networkManager.makeCallRx(request, HttpMethod.GET, Config.class)
                .flatMap(response -> {
                    if (response.getData() != null) {
                        ResponseCacheManager.getInstance().saveResponse(AppConfig.CACHE_KEY, CommonApiManager.CONFIG_API_TAG, null, response.getData());
                        return Observable.just(response.getData());
                    }
                    return Observable.error(new Exception("Invalid config data received"));
                });
    }

    public Observable<TopHeadlinesResponse> fetchTopHeadlines(String countryCode) {
        String url = String.format(TOP_HEADLINES_URL, countryCode);
        BaseRequest request = new BaseRequest.Builder(url, TOP_HEADLINES_API_TAG).build();
        return networkManager.makeCallRx(request, HttpMethod.GET, TopHeadlinesResponse.class)
                .flatMap(baseResponse -> {
                    if (baseResponse.getData() != null && "OK".equalsIgnoreCase(baseResponse.getData().getStatus())) {
                        String cacheKey = AppConstants.TOP_HEADLINES_CACHE_KEY + countryCode;
                        ResponseCacheManager.getInstance().saveResponse(cacheKey, TOP_HEADLINES_API_TAG,
                                null, baseResponse.getData());
                        return Observable.just(baseResponse.getData());
                    }
                    return Observable.error(new Exception("Error in response"));
                });
    }

}
