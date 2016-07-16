package com.march.reaper.mvp.model;

import com.march.bean.WholeAlbumItem;

import java.util.List;

/**
 * Created by march on 16/7/1.
 *
 */
public class WholeAlbumResponse extends BaseResponse {
    private List<WholeAlbumItem> data;

    public List<WholeAlbumItem> getData() {
        return data;
    }

    public void setData(List<WholeAlbumItem> data) {
        this.data = data;
    }
}
