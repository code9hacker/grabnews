package com.bitfault.grabnews.common.storage;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageCacheTable {

    private static final String LOG_TAG = "ImageCacheTable";
    public static final String NAME = "image_cache_table";

    public static final String CREATE_SQL = "create table if not exists " +
            NAME + " (" +
            Column.URL + " text primary key, " +
            Column.IMAGE + " blob not null)";


    public static class Column {
        public static final String URL = "column_url";
        public static final String IMAGE = "column_image";

        private String url;
        private Bitmap image;

        public Column(String url, Bitmap image) {
            this.url = url;
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public Bitmap getImage() {
            return image;
        }
    }

    public static List<Column> getColumns(Cursor cursor) {
        List<Column> columnList = new ArrayList<>();
        if (cursor == null || cursor.isClosed() || !cursor.moveToFirst()) {
            return columnList;
        }
        try {
            do {
                String url = cursor.getString(cursor.getColumnIndex(Column.URL));
                byte[] imageByteArray = cursor.getBlob(cursor.getColumnIndex(Column.IMAGE));
                Bitmap image = BitmapFactory.decodeStream(new ByteArrayInputStream(imageByteArray));
                Column column = new Column(url, image);
                columnList.add(column);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        } finally {
            cursor.close();
        }
        return columnList;
    }

}
