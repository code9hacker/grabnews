package com.bitfault.grabnews.common.model.event;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Event to add fragment to UI
 */
public class AddFragmentEvent extends BaseEvent {

    private Fragment fragment;
    private int containerId;
    private String tag;
    private boolean addToBackStack;

    public AddFragmentEvent(@NonNull Fragment fragment, @IdRes int containerId) {
        super(EventType.EVENT_ADD_FRAGMENT);
        this.fragment = fragment;
        this.containerId = containerId;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public int getContainerId() {
        return containerId;
    }

    public void setAddToBackStack(boolean addToBackStack) {
        this.addToBackStack = addToBackStack;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }
}
