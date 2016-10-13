package com.march.reaper.imodel;

import com.march.quickrvlibs.inter.RvQuickInterface;

import java.util.List;

/**
 * Created by march on 16/6/9.
 *
 */
public class AlbumItemResponse extends BaseResponse {
    List<AlbumItem> data;

    public AlbumItemResponse(int offset, List<AlbumItem> data) {
        this.offset = offset;
        this.data = data;
    }

    public List<AlbumItem> getData() {
        return data;
    }

    public void setData(List<AlbumItem> data) {
        this.data = data;
    }

    public static class AlbumItem implements RvQuickInterface{
        String _id;
        String pic_desc;
        String album_link;
        String pic_src;

        public AlbumItem(String _id, String pic_desc, String album_link, String pic_src) {
            this._id = _id;
            this.pic_desc = pic_desc;
            this.album_link = album_link;
            this.pic_src = pic_src;
        }

        @Override
        public String toString() {
            return "AlbumItem{" +
                    "pic_src='" + pic_src + '\'' +
                    ", pic_desc='" + pic_desc + '\'' +
                    '}';
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getPic_desc() {
            return pic_desc;
        }

        public void setPic_desc(String pic_desc) {
            this.pic_desc = pic_desc;
        }

        public String getAlbum_link() {
            return album_link;
        }

        public void setAlbum_link(String album_link) {
            this.album_link = album_link;
        }

        public String getPic_src() {
            return pic_src;
        }

        public void setPic_src(String pic_src) {
            this.pic_src = pic_src;
        }

        @Override
        public int getRvType() {
            return 0;
        }
    }
}
