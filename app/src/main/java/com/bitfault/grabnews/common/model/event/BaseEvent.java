package com.bitfault.grabnews.common.model.event;

/**
 * Base class of Events.
 */
public class BaseEvent {

    private @EventType
    int type;

    public BaseEvent(@EventType int type) {
        this.type = type;
    }

    @EventType
    public int getType() {
        return type;
    }
}