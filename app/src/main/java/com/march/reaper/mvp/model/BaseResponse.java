package com.march.reaper.mvp.model;

import java.util.List;

/**
 * Created by march on 16/7/2.\
 */
public class BaseResponse {
    protected int status;
    protected int offset;


    public BaseResponse() {
    }

    public int getStatus() {
        return status;
    }

    public int getOffset() {
        return offset;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List getData(){
        return null;
    }
}
