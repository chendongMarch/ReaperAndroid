package com.march.bean;

/**
 * Created by march on 16/7/2.
 * Album基类
 */
public abstract class Album implements java.io.Serializable {
    public abstract String getAlbum_desc();

    public abstract String getKey_words();

    public abstract Boolean getIsFavorite();

    public abstract String getAlbum_link();

}
