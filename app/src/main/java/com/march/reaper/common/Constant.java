package com.march.reaper.common;

/**
 * Created by march on 16/6/30.
 * 常量
 */
public class Constant {

    public static long lastRefreshTime = 0;

    public static final String[] mMenuItem =
            new String[]{
                    "最新推荐",
                    "性感美女",
                    "少女美女",
                    "丝袜美腿",
                    "美乳香臀",
                    "唯美写真",
                    "高清壁纸"};
    //    public static final String[] mMenuItem =
//            new String[]{
//                    "newnew",
//                    "xinggan",
//                    "shaonv",mMenuItem
//                    "swmtswmt",
//                    "mrxtmrxt",
//                    "wmxzwmxz",
//                    "wallpaper"};
    public static final String[] mRecommendType =
            new String[]{
                    "new",
                    "xinggan",
                    "shaonv",
                    "swmt",
                    "mrxt",
                    "wmxz",
                    "wallpaper"};
    public static final String[] MINE_CONTENT_LIST =
            new String[]{
                    "照片收藏",
                    "专辑收藏",
                    "数据离线",
                    "分享给朋友",
                    "检测新版本",
                    "关于"
            };

    public static final int ALBUM_WHOLE = 1;
    public static final int ALBUM_RECOMMEND = 2;
    public static final String KEY_DEFAULT_DATA = "KEY_DEFAULT_DATA";
    public static final String TYPE_ALL_RECOMMEND_ALBUM = "all";

    //专辑分类的题目
    public static final String KEY_ALBUM_TITLE = "KEY_ALBUM_TITLE";
    //专辑的类型
    public static final String KEY_BEAUTY_RECOMMEND_ALBUM_TYPE = "KEY_BEAUTY_RECOMMEND_ALBUM_TYPE";
    //是否是全部专辑页面
    public static final String KEY_IS_WHOLE_ALBUM = "KEY_IS_WHOLE_ALBUM";
    //传递给详情页的数据
    public static final String KEY_ALBUM_DETAIL_SHOW = "KEY_ALBUM_DETAIL_SHOW";
    //查看照片
    public static final String KEY_ALBUM_DETAIL_SCAN = "KEY_ALBUM_DETAIL_SCAN";
    public static final String KEY_ALBUM_DETAIL_LIST_SCAN = "KEY_ALBUM_DETAIL_LIST_SCAN";
    //启动详情页的形式
    public static final String KEY_DETAIL_TYPE = "KEY_DETAIL_TYPE";
    //提前预加载的数量
    public static final int PRE_LOAD_NUM = 4;
    //一次请求的数据量
    public static final int ONECE_QUERY_DATA_NUM = 20;

    public static final String KEY_BEAUTY_ALBUM_TYPE = "KEY_BEAUTY_ALBUM_TYPE";
    public static final String KEY_POS = "KEY_POS";
    public static boolean IsVideoScrolled = false;
}
