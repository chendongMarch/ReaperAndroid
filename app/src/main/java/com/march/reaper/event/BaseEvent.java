package com.march.reaper.event;

/**
 * com.march.reaper.event
 * Created by march on 16/7/21.
 * desc :event bus 事件基类
 */
public class BaseEvent {
    private String msg;

    public BaseEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
