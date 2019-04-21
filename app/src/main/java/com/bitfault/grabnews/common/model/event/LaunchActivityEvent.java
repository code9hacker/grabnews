package com.bitfault.grabnews.common.model.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Event to launch activity
 */
public class LaunchActivityEvent extends BaseEvent {

    private Class<? extends AppCompatActivity> activityClass;
    private Bundle bundle;

    public LaunchActivityEvent(Class<? extends AppCompatActivity> activityClass, Bundle bundle) {
        super(EventType.EVENT_LAUNCH_ACTIVITY);
        this.activityClass = activityClass;
        this.bundle = bundle;
    }

    public Class<? extends AppCompatActivity> getActivityClass() {
        return activityClass;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
