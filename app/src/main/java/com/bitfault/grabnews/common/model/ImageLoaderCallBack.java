package com.bitfault.grabnews.common.model;

import android.graphics.Bitmap;

public interface ImageLoaderCallBack {

    void onImageLoadSuccess(Bitmap bitmap, boolean fromCache);
    void onImageLoadFailure();

}
