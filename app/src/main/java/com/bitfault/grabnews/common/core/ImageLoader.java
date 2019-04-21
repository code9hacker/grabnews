package com.bitfault.grabnews.common.core;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bitfault.grabnews.common.model.ImageLoaderCallBack;
import com.bitfault.grabnews.common.storage.DatabaseHelper;
import com.bitfault.grabnews.common.storage.ImageCacheTable;
import com.bitfault.grabnews.common.util.AppUtils;
import com.bitfault.grabnews.common.util.CollectionUtils;
import com.bitfault.grabnews.common.util.StringUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Image loading and caching helper
 * TODO : implement in-memory LRU cache above DB cache to improve performance
 */
public class ImageLoader {

    private static final String LOG_TAG = "ImageLoader";

    public static void loadImage(ImageView imageView, String url, boolean cache, ImageLoaderCallBack callBack) {
        if(cache) {
            // try loading from cache first
            Bitmap dbCacheBitmap = fetchImageFromDBCache(url);
            if(dbCacheBitmap != null) {
                callBack.onImageLoadSuccess(dbCacheBitmap, true);
                imageView.setImageBitmap(dbCacheBitmap);
                return;
            }
        }
        Glide.with(imageView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(callBack != null) {
                            callBack.onImageLoadFailure();
                        }
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap bitmap = AppUtils.drawableToBitmap(resource);
                        if(callBack != null) {
                            callBack.onImageLoadSuccess(bitmap, false);
                        }
                        saveImageInDBCache(url, bitmap);
                        imageView.setImageDrawable(resource);
                        return true;
                    }
                }).into(imageView);
    }

    private static Bitmap fetchImageFromDBCache(String url) {
        if(StringUtils.isNullEmpty(url)) {
            return null;
        }
        String[] projection = {
                ImageCacheTable.Column.URL,
                ImageCacheTable.Column.IMAGE
        };
        String selection = ImageCacheTable.Column.URL + " = ?";
        String[] selectionArgs = {
                url
        };

        List<ImageCacheTable.Column> columnsList = ImageCacheTable.getColumns(DatabaseHelper.getInstance().readData(ImageCacheTable.NAME,
                projection, selection, selectionArgs));
        if(CollectionUtils.isNullEmpty(columnsList)) {
            return null;
        }
        return columnsList.get(0).getImage();
    }

    private static void saveImageInDBCache(String url, Bitmap bitmap) {
        if(StringUtils.isNullEmpty(url) || bitmap == null) {
            return;
        }
        deleteOldData(url);
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] byteArray = byteBuffer.array();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageCacheTable.Column.URL, url);
        contentValues.put(ImageCacheTable.Column.IMAGE, byteArray);
        DatabaseHelper.getInstance().insertData(ImageCacheTable.NAME, contentValues);
    }

    private static void deleteOldData(String url) {
        String selection = ImageCacheTable.Column.URL + " = ?";
        String[] selectionArgs = {
                url
        };
        DatabaseHelper.getInstance().deleteData(ImageCacheTable.NAME, selection, selectionArgs);
    }

}
