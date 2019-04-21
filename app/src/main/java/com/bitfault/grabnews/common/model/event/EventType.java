package com.bitfault.grabnews.common.model.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        EventType.EVENT_ADD_CHILD_VIEW,
        EventType.EVENT_ADD_FRAGMENT,
        EventType.EVENT_LAUNCH_ACTIVITY
})
@Retention(RetentionPolicy.SOURCE)
public @interface EventType {
    int EVENT_ADD_CHILD_VIEW = 1;
    int EVENT_ADD_FRAGMENT = 2;
    int EVENT_LAUNCH_ACTIVITY = 3;
}
