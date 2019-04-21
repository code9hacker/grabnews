package com.bitfault.grabnews.common.model;

import android.support.annotation.LayoutRes;

import java.util.HashMap;
import java.util.Map;

/**
 * Base generic class of a recyclerview item data
 */
public class BaseRecyclerItemViewData {

    private int viewType;
    private @LayoutRes
    int layoutRes;
    private Map<Integer, Object> bindingModelMap = new HashMap<>();

    public BaseRecyclerItemViewData(int layoutRes) {
        this.layoutRes = layoutRes;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public void addBindingViewModel(int variable, Object data) {
        this.bindingModelMap.put(variable, data);
    }

    public int getViewType() {
        return viewType;
    }

    @LayoutRes
    public int getLayoutRes() {
        return layoutRes;
    }

    public Map<Integer, Object> getBindingModelMap() {
        return bindingModelMap;
    }
}
