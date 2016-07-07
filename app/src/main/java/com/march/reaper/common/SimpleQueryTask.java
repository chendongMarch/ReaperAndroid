package com.march.reaper.common;

import android.os.AsyncTask;

import java.util.List;

/**
 * Created by march on 16/6/30.
 *
 * 一个简单的Task,内置等待过程,异步从数据库中读取数据
 */
public abstract class SimpleQueryTask<T> extends AsyncTask<Void, Void, List<T>> {

    @Override
    protected List<T> doInBackground(Void... params) {
        return query();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<T> list) {
        super.onPostExecute(list);
        afterQuery(list);
    }

    protected abstract List<T> query();

    protected abstract void afterQuery(List<T> list);
}
