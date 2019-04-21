package com.bitfault.grabnews.common.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Http request data model class
 */
public class BaseRequest {

    private @NonNull
    String tag;
    private @NonNull
    String url;
    private @NonNull
    Map<String, String> headers;
    private @NonNull
    Map<String, String> params;
    private @Nullable
    Object data;
    private @NonNull
    String mediaType;
    private long timeout;
    // TODO : add caching strategy here

    private BaseRequest(@NonNull String tag,
                        @NonNull String url,
                        @NonNull Map<String, String> headers,
                        @NonNull Map<String, String> params,
                        @Nullable Object data,
                        @NonNull String mediaType,
                        long timeout) {
        this.tag = tag;
        this.url = url;
        this.headers = headers;
        this.params = params;
        this.data = data;
        this.mediaType = mediaType;
        this.timeout = timeout;
    }

    @NonNull
    public String getTag() {
        return tag;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void addHeader(String name, String val) {
        headers.put(name, val);
    }

    @NonNull
    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addParam(String name, String val) {
        params.put(name, val);
    }

    @NonNull
    public Map<String, String> getParams() {
        return params;
    }

    @Nullable
    public Object getData() {
        return data;
    }

    public long getTimeout() {
        return timeout;
    }

    @NonNull
    public String getMediaType() {
        return mediaType;
    }

    public static class Builder {
        private String url;
        private String tag;
        private Map<String, String> headers = new HashMap<>();
        private Map<String, String> params = new HashMap<>();
        private Object data;
        private String mediaType = NetworkManager.MEDIA_TYPE_JSON;
        private long timeout = NetworkManager.DEFAULT_TIMEOUT;

        public Builder(String url, String tag) {
            this.url = url;
            this.tag = tag;
        }

        public Builder header(@NonNull String key, @NonNull String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder headers(@NonNull Map<String, String> headers) {
            this.headers.clear();
            this.headers.putAll(headers);
            return this;
        }

        public Builder param(@NonNull String key, @NonNull String value) {
            this.params.put(key, value);
            return this;
        }

        public Builder params(@NonNull Map<String, String> headers) {
            this.params.clear();
            this.params.putAll(headers);
            return this;
        }

        public Builder data(@NonNull Object data) {
            this.data = data;
            return this;
        }

        public Builder mediaType(@NonNull String mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public Builder timeout(long timeout) {
            if (timeout <= 0) {
                throw new IllegalArgumentException("Timeout cannot be zero or negative");
            }
            this.timeout = timeout;
            return this;
        }

        public BaseRequest build() {
            return new BaseRequest(this.tag, this.url, this.headers, this.params, this.data, this.mediaType, this.timeout);
        }
    }
}
