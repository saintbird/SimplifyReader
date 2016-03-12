/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.life.lightlife;

import android.app.Application;
import android.content.Context;

import com.github.obsessive.library.base.BaseAppManager;
import com.life.lightlife.api.ApiConstants;
import com.life.lightlife.greendao.DaoMaster;
import com.life.lightlife.greendao.DaoSession;
import com.life.lightlife.jiandan.cache.BaseCache;
import com.life.lightlife.jiandan.view.imageloader.ImageLoadProxy;
import com.life.lightlife.utils.ImageLoaderHelper;
import com.life.lightlife.utils.VolleyHelper;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/3/9.
 * Description: App Application Context
 */
public class LightLifeApplication extends Application {

    private static Context mContext;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        MobclickAgent.setDebugMode(true);
        MobclickAgent.updateOnlineConfig(this);
        MobclickAgent.openActivityDurationTrack(false);
        UmengUpdateAgent.update(this);

        VolleyHelper.getInstance().init(this);
        ImageLoadProxy.initImageLoader(this);
        ImageLoader.getInstance().init(ImageLoaderHelper.getInstance(this).getImageLoaderConfiguration(ApiConstants.Paths.IMAGE_LOADER_CACHE_PATH));
    }

    public static Context getContext() {
        return mContext;
    }

    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, BaseCache.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        MobclickAgent.onKillProcess(this);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
