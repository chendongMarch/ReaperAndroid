package com.march.reaper.mvp.model;

import com.march.bean.WholeAlbumItem;

import java.util.List;

/**
 * Created by march on 16/7/1.
 *
 */
public class WholeAlbumResponse implements BaseResponse{
    private List<WholeAlbumItem> data;
    private int offset;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<WholeAlbumItem> getData() {
        return data;
    }

    public void setData(List<WholeAlbumItem> data) {
        this.data = data;
    }
}
