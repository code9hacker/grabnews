package com.bitfault.grabnews.common.model.event;

import android.support.annotation.LayoutRes;

import java.util.HashMap;
import java.util.Map;

/**
 * Event to add child view to a view-group
 */
public class AddChildViewEvent extends BaseEvent {

    @LayoutRes
    int layoutRes;
    private String tag;
    private Map<Integer, Object> viewModelMap = new HashMap<>();

    public AddChildViewEvent(@LayoutRes int layoutRes) {
        super(EventType.EVENT_ADD_CHILD_VIEW);
        this.layoutRes = layoutRes;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public void addViewModel(int key, Object viewModel) {
        this.viewModelMap.put(key, viewModel);
    }

    public int getLayoutRes() {
        return layoutRes;
    }

    public Map<Integer, Object> getViewModelMap() {
        return viewModelMap;
    }
}
