package com.march.reaper.imodel.bean;
import com.march.reaper.imodel.bean.BeautyAlbum;
import com.march.lib.adapter.common.IAdapterModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AlbumItemCollection implements IAdapterModel, java.io.Serializable {


    @Id
    private String album_link;
    private String key_words;
    private String album_cover;
    private String album_desc;


    public AlbumItemCollection() {
    }

    public AlbumItemCollection(String album_link) {
        this.album_link = album_link;
    }

    @Keep
    public AlbumItemCollection(String album_link, String key_words, String album_cover, String album_desc) {
        this.album_link = album_link;
        this.key_words = key_words;
        this.album_cover = album_cover;
        this.album_desc = album_desc;
    }

    public String getAlbum_link() {
        return album_link;
    }

    public void setAlbum_link(String album_link) {
        this.album_link = album_link;
    }

    public String getKey_words() {
        return key_words;
    }

    public void setKey_words(String key_words) {
        this.key_words = key_words;
    }

    public String getAlbum_cover() {
        return album_cover;
    }

    public void setAlbum_cover(String album_cover) {
        this.album_cover = album_cover;
    }

    public String getAlbum_desc() {
        return album_desc;
    }

    public void setAlbum_desc(String album_desc) {
        this.album_desc = album_desc;
    }

    public AlbumItemCollection(BeautyAlbum item) {
        this.album_link = item.getAlbum_link();
        this.key_words = item.getKey_words();
        this.album_cover = item.getAlbum_cover();
        this.album_desc = item.getAlbum_desc();
    }

    @Override
    public int getRvType() {
        return 0;
    }
}
