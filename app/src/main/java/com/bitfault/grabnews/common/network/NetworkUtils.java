package com.bitfault.grabnews.common.network;

import android.support.annotation.NonNull;

import com.bitfault.grabnews.common.util.CollectionUtils;
import com.bitfault.grabnews.common.util.StringUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtils {

    private static final String QUERY_KEY = "?";

    private NetworkUtils() {
        // NOP
    }


    public static <T> Request makeOkHttpRequest(@NonNull BaseRequest request, @HttpMethod int method) {
        Request.Builder builder = new Request.Builder()
                .tag(request.getTag())
                .url(getUrlWithQueryParams(request))
                .headers(Headers.of(request.getHeaders()));
        setHttpMethodToCall(builder, method, request);
        return builder.build();
    }

    public static void setHttpMethodToCall(@NonNull Request.Builder builder, @HttpMethod int method, @NonNull BaseRequest request) {
        switch (method) {
            case HttpMethod.GET:
                builder.get();
                break;
            case HttpMethod.POST:
                builder.post(RequestBody.create(MediaType.parse(request.getMediaType()), new Gson().toJson(request.getData())));
                break;
            case HttpMethod.DELETE:
                builder.delete(RequestBody.create(MediaType.parse(request.getMediaType()), new Gson().toJson(request.getData())));
                break;
            case HttpMethod.PUT:
                builder.put(RequestBody.create(MediaType.parse(request.getMediaType()), new Gson().toJson(request.getData())));
                break;
            default:
                break;
        }
    }

    public static <T> BaseResponse<T> makeBaseResponseFromOkHttp(Response response, Class<T> classOfT) throws IOException {
        BaseResponse<T> baseResponse = new BaseResponse<>();
        baseResponse.setData(response.body() != null ? new Gson().fromJson(response.body().string(), classOfT) : null);
        baseResponse.setHeaders(getHeadersFromOkHttp(response.headers()));
        return baseResponse;
    }

    public static String getUrlWithQueryParams(@NonNull BaseRequest request) {
        if (CollectionUtils.isNullEmpty(request.getParams())) {
            return request.getUrl();
        }
        StringBuilder sb = new StringBuilder(request.getUrl());
        if (!request.getUrl().contains(QUERY_KEY)) {
            sb.append(QUERY_KEY);
        } else if (!request.getUrl().endsWith(QUERY_KEY)) {
            sb.append("&");
        }
        for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    public static Map<String, String> getHeadersFromOkHttp(Headers okHttpHeaders) {
        Map<String, String> headers = new HashMap<>();
        for (String name : okHttpHeaders.names()) {
            if (StringUtils.isNotNullEmpty(okHttpHeaders.get(name))) {
                headers.put(name, okHttpHeaders.get(name));
            }
        }
        return headers;
    }

}
