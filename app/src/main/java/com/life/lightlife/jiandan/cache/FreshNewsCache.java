package com.life.lightlife.jiandan.cache;

import android.content.Context;

import com.life.lightlife.LightLifeApplication;
import com.life.lightlife.greendao.FreshNewsCacheDao;
import com.life.lightlife.jiandan.model.FreshNews;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import de.greenrobot.dao.query.QueryBuilder;

public class FreshNewsCache extends BaseCache {

    private static FreshNewsCache instance;
    private static FreshNewsCacheDao mFreshNewsCacheDao;

    private FreshNewsCache() {
    }

    public static FreshNewsCache getInstance(Context context) {

        if (instance == null) {

            synchronized (FreshNewsCache.class) {
                if (instance == null) {
                    instance = new FreshNewsCache();
                }
            }

            mDaoSession = LightLifeApplication.getDaoSession(context);
            mFreshNewsCacheDao = mDaoSession.getFreshNewsCacheDao();
        }
        return instance;
    }

    public void clearAllCache() {
        mFreshNewsCacheDao.deleteAll();
    }

    @Override
    public ArrayList<FreshNews> getCacheByPage(int page) {

        QueryBuilder<com.life.lightlife.greendao.FreshNewsCache> query = mFreshNewsCacheDao.queryBuilder().where(FreshNewsCacheDao
                .Properties.Page.eq("" + page));

        if (query.list().size() > 0) {
            try {
                return FreshNews.parseCache(new JSONArray(query.list().get(0)
                        .getResult()));
            } catch (JSONException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public void addResultCache(String result, int page) {

        com.life.lightlife.greendao.FreshNewsCache freshNewsCache = new com.life.lightlife.greendao.FreshNewsCache();
        freshNewsCache.setResult(result);
        freshNewsCache.setPage(page);
        freshNewsCache.setTime(System.currentTimeMillis());

        mFreshNewsCacheDao.insert(freshNewsCache);
    }

}