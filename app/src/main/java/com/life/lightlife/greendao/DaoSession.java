package com.life.lightlife.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.life.lightlife.greendao.FreshNewsCache;
import com.life.lightlife.greendao.FreshNewsCacheDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig freshNewsCacheDaoConfig;
    private final FreshNewsCacheDao freshNewsCacheDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        freshNewsCacheDaoConfig = daoConfigMap.get(FreshNewsCacheDao.class).clone();
        freshNewsCacheDaoConfig.initIdentityScope(type);
        freshNewsCacheDao = new FreshNewsCacheDao(freshNewsCacheDaoConfig, this);
        registerDao(FreshNewsCache.class, freshNewsCacheDao);
    }
    
    public void clear() {
        freshNewsCacheDaoConfig.getIdentityScope().clear();
    }

    public FreshNewsCacheDao getFreshNewsCacheDao() {
        return freshNewsCacheDao;
    }

}
