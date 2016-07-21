package com.march.reaper.event;

/**
 * com.march.reaper.event
 * Created by march on 16/7/21.
 * desc :
 */
public class SucceedEntryAppEvent extends BaseEvent {
    public SucceedEntryAppEvent(String msg) {
        super(msg);
    }

    public static final String EVENT_SUCCEED_ENTRY_APP = "EVENT_SUCCEED_ENTRY_APP";
}
