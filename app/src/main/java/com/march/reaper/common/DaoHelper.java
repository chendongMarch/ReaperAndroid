package com.march.reaper.common;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.march.reaper.db.AlbumDetailDao;
import com.march.reaper.db.BeautyAlbumDao;
import com.march.reaper.db.DaoMaster;
import com.march.reaper.db.DaoSession;
import com.march.reaper.db.DetailCollectionDao;
import com.march.reaper.imodel.AlbumDetailResponse;
import com.march.reaper.imodel.BeautyAlbumResponse;
import com.march.reaper.imodel.bean.AlbumDetail;
import com.march.reaper.imodel.bean.AlbumItemCollection;
import com.march.reaper.imodel.bean.BeautyAlbum;
import com.march.reaper.imodel.bean.DetailCollection;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;


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

    public DetailCollectionDao getDetailCollectionDao() {
        return mDaoSession.getDetailCollectionDao();
    }

    public BeautyAlbumDao getBeautyAlbumDao() {
        return mDaoSession.getBeautyAlbumDao();
    }


    public <T extends AbstractDao> T getDao(Class cls) {
        if (cls == AlbumDetail.class || cls == AlbumDetailResponse.class) {
            return (T) mDaoSession.getAlbumDetailDao();
        } else if (cls == BeautyAlbum.class || cls == BeautyAlbumResponse.class) {
            return (T) mDaoSession.getBeautyAlbumDao();
        } if (cls == AlbumItemCollection.class) {
            return (T) mDaoSession.getAlbumItemCollectionDao();
        } else if (cls == DetailCollection.class) {
            return (T) mDaoSession.getDetailCollectionDao();
        }
        return null;
    }

}
