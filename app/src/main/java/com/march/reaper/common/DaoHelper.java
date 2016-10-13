package com.march.reaper.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.march.bean.AlbumDetail;
import com.march.bean.AlbumItemCollection;
import com.march.bean.DetailCollection;
import com.march.bean.RecommendAlbumItem;
import com.march.bean.WholeAlbumItem;
import com.march.dao.AlbumDetailDao;
import com.march.dao.DaoMaster;
import com.march.dao.DaoSession;
import com.march.dao.RecommendAlbumItemDao;
import com.march.dao.WholeAlbumItemDao;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.imodel.RecommendAlbumResponse;
import com.march.reaper.imodel.WholeAlbumResponse;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by march on 16/6/30.
 * 获取数据库连接的类
 */
public class DaoHelper {

    private DaoSession mDaoSession;

    private DaoHelper() {

    }

    private static DaoHelper mInst;

    public static DaoHelper get() {
        if (mInst == null) {
            synchronized (DaoHelper.class) {
                if (mInst == null) {
                    mInst = new DaoHelper();
                }
            }
        }
        return mInst;
    }


    //    初始化数据库
    public void setupDatabase(Context context) {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        // 在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }

    public AlbumDetailDao getAlbumDetailDao() {
        return mDaoSession.getAlbumDetailDao();
    }

    public RecommendAlbumItemDao getRecommendAlbumItemDao() {
        return mDaoSession.getRecommendAlbumItemDao();
    }

    public WholeAlbumItemDao getWholeAlbumItemDao() {
        return mDaoSession.getWholeAlbumItemDao();
    }

    public <T extends AbstractDao> T getDao(Class cls) {
        if (cls == AlbumDetail.class || cls == AlbumDetailResponse.class) {
            return (T) mDaoSession.getAlbumDetailDao();
        } else if (cls == RecommendAlbumItem.class || cls == RecommendAlbumResponse.class) {
            return (T) mDaoSession.getRecommendAlbumItemDao();
        } else if (cls == WholeAlbumItem.class || cls == WholeAlbumResponse.class) {
            return (T) mDaoSession.getWholeAlbumItemDao();
        } else if (cls == AlbumItemCollection.class) {
            return (T) mDaoSession.getAlbumItemCollectionDao();
        } else if (cls == DetailCollection.class) {
            return (T) mDaoSession.getDetailCollectionDao();
        }
        return null;
    }

}
