package com.march.reaper.common;


/**
 * Created by march on 16/6/30.
 * API
 */
public class API {

    //    public static final String BASE_TEST = "http://192.168.1.132:3000";
    public static final String BASE_TEST = "http://192.168.92.142:3000";//y904-2
//        public static final String BASE_TEST = "http://192.168.2.145:3000";//lengwong
//    public static final String BASE_TEST = "http://192.168.31.132:3000";

    public static final String BASE_ONLINE = "http://reapernode.tiger.mopaasapp.com";
    public static final String BASE = BASE_ONLINE;
    public static final String GET_RECOMMEND_ALBUM = "";
    public static final String GET_WHOLE_ALBUM = "";
    public static final String GET_ALBUM_DETAIL = "";
    //离线推荐专辑
    public static final String GET_OFFLINE_RECOMMEND = BASE + "/offline/recommend";
    public static final String GET_OFFLINE_WHOLE = BASE + "/offline/whole";
    public static final String GET_OFFLINE_DETAIL = BASE + "/offline/detail";

    public static final String GET_SCAN_RECOMMEND = BASE + "/scan/recommend";
    public static final String GET_SCAN_WHOLE = BASE + "/scan/whole";
    public static final String GET_SCAN_DETAIL = BASE + "/scan/detail";
    public static final String GET_LUCKY = BASE + "/lucky";
    public static final String POST_ADD_USER = BASE + "/addUser";
    public static final String POST_CHECK_USER = BASE + "/checkUser";
    public static final String GET_CHECK_VERSION = BASE + "/checkVersion";
    public static final String POST_AUTO_REGISTER = BASE + "/autoRegister";
    public static final String POST_AUTO_Record= BASE + "/autoRecord";
}
