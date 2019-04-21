package com.bitfault.grabnews.common.ui;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bitfault.grabnews.common.core.GrabNewsApplication;
import com.bitfault.grabnews.common.core.ImageLoader;
import com.bitfault.grabnews.common.model.BaseRecyclerItemViewData;
import com.bitfault.grabnews.common.model.ImageLoaderCallBack;
import com.bitfault.grabnews.common.model.event.AddChildViewEvent;
import com.bitfault.grabnews.common.model.event.BaseEvent;
import com.bitfault.grabnews.common.ui.recyclerView.BaseRecyclerViewAdapter;
import com.bitfault.grabnews.common.util.CollectionUtils;
import com.bitfault.grabnews.common.util.StringUtils;

import java.util.List;
import java.util.Map;

public class CommonBindingAdapter {

    @BindingAdapter("addChildEvent")
    public static void addChild(ViewGroup viewGroup, BaseEvent baseEvent) {
        if(baseEvent == null) {
            return;
        }
        AddChildViewEvent addChildViewEvent = (AddChildViewEvent) baseEvent;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, addChildViewEvent.getLayoutRes(), viewGroup, false);
        if(CollectionUtils.isNotNullEmpty(addChildViewEvent.getViewModelMap())) {
            for(Map.Entry<Integer, Object> viewModel : addChildViewEvent.getViewModelMap().entrySet()) {
                binding.setVariable(viewModel.getKey(), viewModel.getValue());
            }
        }
        viewGroup.addView(binding.getRoot());
    }

    @BindingAdapter("visibleGone")
    public static void visibleGone(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("recyclerDataList")
    public static void recyclerDataList(RecyclerView recyclerView, List<BaseRecyclerItemViewData> dataList) {
        if(recyclerView.getAdapter() == null || !(recyclerView.getAdapter() instanceof BaseRecyclerViewAdapter)) {
            BaseRecyclerViewAdapter adapter = new BaseRecyclerViewAdapter(dataList);
            recyclerView.setAdapter(adapter);
        } else {
            ((BaseRecyclerViewAdapter) recyclerView.getAdapter()).updateDataList(dataList);
        }
    }

    @BindingAdapter(value = {"imageUrl", "placeholder"}, requireAll = false)
    public static void bindImageUrl(ImageView imageView, String imageUrl, @DrawableRes int placeholder) {
        imageView.setImageBitmap(null);
        if(StringUtils.isNullEmpty(imageUrl)) {
            if(placeholder != 0) {
                imageView.setImageResource(placeholder);
            }
            return;
        }

        ImageLoader.loadImage(imageView, imageUrl, true, new ImageLoaderCallBack() {
            @Override
            public void onImageLoadSuccess(Bitmap bitmap, boolean fromCache) {
                imageView.setImageDrawable(new BitmapDrawable(GrabNewsApplication.mContext.getResources(), bitmap));
            }

            @Override
            public void onImageLoadFailure() {

            }
        });
    }

}
