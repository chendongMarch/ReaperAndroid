package com.march.reaper.common;


import com.march.reaper.db.AlbumDetailDao;
import com.march.reaper.db.AlbumItemCollectionDao;
import com.march.reaper.db.BeautyAlbumDao;
import com.march.reaper.db.DetailCollectionDao;
import com.march.reaper.imodel.bean.AlbumDetail;
import com.march.reaper.imodel.bean.AlbumItemCollection;
import com.march.reaper.imodel.bean.BeautyAlbum;
import com.march.reaper.imodel.bean.DetailCollection;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by march on 16/6/30.
 * 数据库结构化查询
 */
public class DbHelper {

    private static DbHelper mInst;

    private DbHelper() {

    }

    public static DbHelper get() {
        if (mInst == null) {
            synchronized (DbHelper.class) {
                if (mInst == null) {
                    mInst = new DbHelper();
                }
            }
        }
        return mInst;
    }

    //查询指定数据库的数量
    public long queryCount(Class cls) {
        QueryBuilder queryBuilder = DaoHelper.get().
                getDao(cls).queryBuilder();
        return queryBuilder.count();
    }


    //查询完毕接口
    public interface OnQueryReadyListener<T> {
        void queryReady(List<T> list);
    }


    public void queryDetailCollection(final int offset, final int limit, final OnQueryReadyListener<DetailCollection> onQueryReadyListener) {
        new SimpleQueryTask<DetailCollection>() {
            @Override
            protected List<DetailCollection> query() {
                Query<DetailCollection> query = DaoHelper.get()
                        .<DetailCollectionDao>getDao(DetailCollection.class).queryBuilder()
                        .offset(offset).limit(limit)
                        .build();
                return query.list();
            }

            @Override
            protected void afterQuery(List<DetailCollection> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }


    public void queryAlbumCollection(final int offset, final int limit, final OnQueryReadyListener<AlbumItemCollection> onQueryReadyListener) {
        new SimpleQueryTask<AlbumItemCollection>() {
            @Override
            protected List<AlbumItemCollection> query() {
                Query<AlbumItemCollection> query = DaoHelper.get()
                        .<AlbumItemCollectionDao>getDao(AlbumItemCollection.class).queryBuilder()
                        .offset(offset).limit(limit)
                        .build();
                return query.list();
            }

            @Override
            protected void afterQuery(List<AlbumItemCollection> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }

    //分页加载所有的专辑
    public void queryWholeAlbum(final int skip, final int limit, final OnQueryReadyListener<BeautyAlbum> onQueryReadyListener) {
        new SimpleQueryTask<BeautyAlbum>() {
            @Override
            protected List<BeautyAlbum> query() {
                Query<BeautyAlbum> query = DaoHelper.get()
                        .getBeautyAlbumDao().queryBuilder()
                        .offset(skip).limit(limit)
                        .build();
                return query.list();
            }

            @Override
            protected void afterQuery(List<BeautyAlbum> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }


    //分页加载   所有推荐专辑 || 某类专辑
    public void queryAllRecommendAlbum(final String type, final int skip, final int limit, final OnQueryReadyListener<BeautyAlbum> onQueryReadyListener) {
        new SimpleQueryTask<BeautyAlbum>() {
            @Override
            protected List<BeautyAlbum> query() {
                queryCount(BeautyAlbum.class);
                QueryBuilder<BeautyAlbum> queryBuilder = DaoHelper.get()
                        .getBeautyAlbumDao().queryBuilder();
                queryBuilder.offset(skip).limit(limit);
                if (type != null)
                    queryBuilder.where(BeautyAlbumDao.Properties.Album_type.eq(type));
                queryBuilder.build();
                return queryBuilder.list();
            }

            @Override
            protected void afterQuery(List<BeautyAlbum> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }


    //查询专辑的详情
    public void queryAlbumDetail(final String albumLink, final int skip, final int limit, final OnQueryReadyListener<AlbumDetail> onQueryReadyListener) {
        new SimpleQueryTask<AlbumDetail>() {
            @Override
            protected List<AlbumDetail> query() {
                Query<AlbumDetail> query = DaoHelper.get()
                        .getAlbumDetailDao().queryBuilder()
                        .where(AlbumDetailDao.Properties.Album_link.eq(albumLink))
                        .offset(skip).limit(limit)
                        .build();
                return query.list();
            }

            @Override
            protected void afterQuery(List<AlbumDetail> list) {
                if (onQueryReadyListener != null) {
                    onQueryReadyListener.queryReady(list);
                }
            }
        }.execute();
    }

    public void addAlbumCollection(AlbumItemCollection col) {
        DaoHelper.get().<AlbumItemCollectionDao>getDao(AlbumItemCollection.class).insert(col);
    }

    public void removeAlbumCollection(AlbumItemCollection col) {
        DaoHelper.get().<AlbumItemCollectionDao>getDao(AlbumItemCollection.class).deleteByKey(col.getAlbum_link());
    }

    public void addDetailCollection(DetailCollection col) {
        DaoHelper.get().<DetailCollectionDao>getDao(DetailCollection.class).insert(col);
    }

    public void removeDetailCollection(DetailCollection col) {
        DaoHelper.get().<DetailCollectionDao>getDao(DetailCollection.class).deleteByKey(col.getPhoto_src());
    }


    public boolean isAlbumCollection(AlbumItemCollection col) {
        QueryBuilder<AlbumItemCollection> queryBuilder = DaoHelper.get()
                .<AlbumItemCollectionDao>getDao(AlbumItemCollection.class).queryBuilder()
                .where(AlbumItemCollectionDao.Properties.Album_link.eq(col.getAlbum_link()));
        long count = queryBuilder.count();
        return count > 0;
    }

    public boolean isDetailCollection(DetailCollection col) {
        QueryBuilder<DetailCollection> queryBuilder = DaoHelper.get()
                .<DetailCollectionDao>getDao(DetailCollection.class).queryBuilder()
                .where(DetailCollectionDao.Properties.Photo_src.eq(col.getPhoto_src()));
        long count = queryBuilder.count();
        return count > 0;
    }


}
