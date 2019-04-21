package com.bitfault.grabnews.common.network;

import android.content.ContentValues;

import com.bitfault.grabnews.common.storage.DatabaseHelper;
import com.bitfault.grabnews.common.storage.HttpResponseCacheTable;
import com.bitfault.grabnews.common.util.CollectionUtils;
import com.bitfault.grabnews.common.util.StringUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Http Json Response Caching Helper class
 */
public class ResponseCacheManager {

    private static ResponseCacheManager sInstance;

    public static ResponseCacheManager getInstance() {
        if (sInstance == null) {
            synchronized (ResponseCacheManager.class) {
                if (sInstance == null) {
                    sInstance = new ResponseCacheManager();
                }
            }
        }
        return sInstance;
    }

    private ResponseCacheManager() {

    }

    public <T> T fetchResponse(String cacheKey, Class<T> classofT) {
        List<HttpResponseCacheTable.Column> cacheResponseList = getCacheData(cacheKey);
        if (CollectionUtils.isNullEmpty(cacheResponseList)) {
            return null;
        }
        if (StringUtils.isNullEmpty(cacheResponseList.get(0).getResponseBody())) {
            return null;
        }
        return new Gson().fromJson(cacheResponseList.get(0).getResponseBody(), classofT);
    }

    public void saveResponse(String cacheKey, String requestTag, Object requestBody, Object responseBody) {
        deleteOldData(cacheKey);
        ContentValues contentValues = new ContentValues();
        contentValues.put(HttpResponseCacheTable.Column.CACHE_KEY, cacheKey);
        contentValues.put(HttpResponseCacheTable.Column.REQUEST_TAG, requestTag);
        contentValues.put(HttpResponseCacheTable.Column.REQUEST_BODY, requestBody == null ? "{}" : new Gson().toJson(requestBody));
        contentValues.put(HttpResponseCacheTable.Column.RESPONSE_BODY, responseBody == null ? "{}" : new Gson().toJson(responseBody));
        DatabaseHelper.getInstance().insertData(HttpResponseCacheTable.NAME, contentValues);
    }

    private void deleteOldData(String cacheKey) {
        String selection = HttpResponseCacheTable.Column.CACHE_KEY + " = ?";
        String[] selectionArgs = {
                cacheKey
        };
        DatabaseHelper.getInstance().deleteData(HttpResponseCacheTable.NAME, selection, selectionArgs);
    }

    private List<HttpResponseCacheTable.Column> getCacheData(String cacheKey) {
        String[] projection = {
                HttpResponseCacheTable.Column.CACHE_KEY,
                HttpResponseCacheTable.Column.REQUEST_TAG,
                HttpResponseCacheTable.Column.REQUEST_BODY,
                HttpResponseCacheTable.Column.RESPONSE_BODY
        };
        String selection = HttpResponseCacheTable.Column.CACHE_KEY + " = ?";
        String[] selectionArgs = {
                cacheKey
        };

        return HttpResponseCacheTable.getColumns(DatabaseHelper.getInstance().readData(HttpResponseCacheTable.NAME,
                projection, selection, selectionArgs));
    }

}
