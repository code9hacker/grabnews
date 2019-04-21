package com.bitfault.grabnews.common.network;

import android.support.annotation.NonNull;

import com.bitfault.grabnews.common.core.GrabNewsApplication;
import com.bitfault.grabnews.common.core.ThreadPoolManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Manages Http calls. This is built upon OkHttp implementation
 */
@Singleton
public class NetworkManager {

    private static final String LOG_TAG = "NetworkManager";
    private static final int CONNECTION_LIMIT = 5;
    private static final long KEEP_ALIVE_DURATION = 5 * 60 * 1000L;
    public static final long DEFAULT_TIMEOUT = 30 * 1000L;
    public static final String MEDIA_TYPE_JSON = "application/json; charset=utf-8";
    private OkHttpClient okHttpClient;

    @Inject
    ThreadPoolManager threadPoolManager;

    @Inject
    public NetworkManager() {
        GrabNewsApplication.applicationComponent.inject(this);
        ConnectionPool connectionPool = new ConnectionPool(CONNECTION_LIMIT, KEEP_ALIVE_DURATION, TimeUnit.MILLISECONDS);
        okHttpClient = new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    public <T> BaseResponse<T> makeCall(@NonNull BaseRequest request, @HttpMethod int method,
                                        Class<T> classOfT) throws IOException {
        OkHttpClient client = getNewOkHttpClient(request.getTimeout());
        Response response = client.newCall(NetworkUtils.makeOkHttpRequest(request, method)).execute();
        if (response.isSuccessful()) {
            return NetworkUtils.makeBaseResponseFromOkHttp(response, classOfT);
        }
        throw new HttpResponseException("UnSuccessful response received for tag = " + request.getTag(),
                response.code(), response.body() != null ? response.body().string() : null);
    }

    private OkHttpClient getNewOkHttpClient(long timeout) {
        return okHttpClient.newBuilder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
    }

    public <T> Observable<BaseResponse<T>> makeCallRx(@NonNull final BaseRequest request, @HttpMethod final int method,
                                                      final Class<T> classOfT) {
        return Observable.fromCallable(() -> makeCall(request, method, classOfT))
                .subscribeOn(Schedulers.from(threadPoolManager.getNetworkExecutor()))
                .observeOn(AndroidSchedulers.mainThread());
    }

}
