package com.march.reaper.common;

/**
 * Created by march on 16/6/30.
 * API
 */
public class API {
    public static final String BASE = "http://192.168.1.132:3000";
    public static final String GET_RECOMMEND_ALBUM = "";
    public static final String GET_WHOLE_ALBUM = "";
    public static final String GET_ALBUM_DETAIL = "";
    //离线推荐专辑
    public static final String GET_OFFLINE_RECOMMEND = BASE + "/findRecommend";
    public static final String GET_OFFLINE_WHOLE = BASE + "/findWholeAlbum";
    public static final String GET_OFFLINE_DETAIL = BASE + "/findAlbumDetail";
}
