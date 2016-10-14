package com.march.reaper.imodel;

import com.march.bean.BeautyAlbum;

import java.util.List;

/**
 * Project  : Reaper
 * Package  : com.march.reaper.imodel
 * CreateAt : 2016/10/14
 * Describe :
 *
 * @author chendong
 */

public class BeautyAlbumResponse extends BaseResponse {
    List<BeautyAlbum> data;

    public BeautyAlbumResponse(List<BeautyAlbum> data) {
        this.data = data;
    }

    @Override
    public List<BeautyAlbum> getData() {
        return data;
    }

    public void setData(List<BeautyAlbum> data) {
        this.data = data;
    }
}
