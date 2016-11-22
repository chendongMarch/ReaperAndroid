package com.march.reaper.imodel;

import java.util.List;

/**
 * Created by march on 16/7/2.\
 */
public class BaseResponse<D> {
    private int status;
    private int offset;
    private List<D> data;

    public void setData(List<D> data) {
        this.data = data;
    }

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

    public List<D> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", offset=" + offset +
                ", data=" + data.toString() +
                '}';
    }
}
