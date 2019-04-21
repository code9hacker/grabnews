package com.bitfault.grabnews.common.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Http response data model class
 * @param <T> Type of response
 */
public class BaseResponse<T> {

    private @Nullable
    T data;
    private @NonNull
    Map<String, String> headers = new HashMap<>();

    public void setData(@Nullable T data) {
        this.data = data;
    }

    public void setHeaders(@NonNull Map<String, String> headers) {
        this.headers = headers;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @NonNull
    public Map<String, String> getHeaders() {
        return headers;
    }
}
