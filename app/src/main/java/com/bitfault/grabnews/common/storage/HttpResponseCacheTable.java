package com.bitfault.grabnews.common.storage;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Database Table model class for Http Response cache data
 */
public class HttpResponseCacheTable {

    public static final String NAME = "http_response_cache_table";

    public static final String CREATE_SQL = "create table if not exists " +
            NAME + " (" +
            Column.CACHE_KEY + " text primary key, " +
            Column.REQUEST_TAG + " text, " +
            Column.REQUEST_BODY + " text, " +
            Column.RESPONSE_BODY + " text)";

    public static class Column {

        public static final String CACHE_KEY = "cache_key";
        public static final String REQUEST_TAG = "request_tag";
        public static final String REQUEST_BODY = "request_body";
        public static final String RESPONSE_BODY = "response_body";
        // TODO : add expiration time and support multiple types other than json

        private String cacheKey;
        private String requestTag;
        private String requestBody;
        private String responseBody;

        public Column(String cacheKey, String requestTag, String requestBody, String responseBody) {
            this.cacheKey = cacheKey;
            this.requestTag = requestTag;
            this.requestBody = requestBody;
            this.responseBody = responseBody;
        }

        public String getCacheKey() {
            return cacheKey;
        }

        public String getRequestTag() {
            return requestTag;
        }

        public String getRequestBody() {
            return requestBody;
        }

        public String getResponseBody() {
            return responseBody;
        }
    }

    public static List<Column> getColumns(Cursor cursor) {
        List<Column> columnList = new ArrayList<>();
        if (cursor == null || cursor.isClosed() || !cursor.moveToFirst()) {
            return columnList;
        }
        try {
            do {
                String cacheKey = cursor.getString(cursor.getColumnIndex(Column.CACHE_KEY));
                String requestTag = cursor.getString(cursor.getColumnIndex(Column.REQUEST_TAG));
                String requestBody = cursor.getString(cursor.getColumnIndex(Column.REQUEST_BODY));
                String responseBody = cursor.getString(cursor.getColumnIndex(Column.RESPONSE_BODY));
                Column column = new Column(cacheKey, requestTag, requestBody, responseBody);
                columnList.add(column);
            } while (cursor.moveToNext());
        } finally {
            cursor.close();
        }
        return columnList;
    }
}
