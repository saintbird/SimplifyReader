package com.life.lightlife.jiandan.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.life.lightlife.BuildConfig;
import com.life.lightlife.jiandan.net.RequestManager;
import com.life.lightlife.jiandan.utils.logger.LogLevel;
import com.life.lightlife.jiandan.utils.logger.Logger;
import com.life.lightlife.jiandan.view.imageloader.ImageLoadProxy;


public class BaseFragment extends Fragment implements ConstantString {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.FULL).hideThreadInfo();
        } else {
            Logger.init(getClass().getSimpleName()).setLogLevel(LogLevel.NONE).hideThreadInfo();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
        ImageLoadProxy.getImageLoader().clearMemoryCache();
    }

    protected void executeRequest(Request request) {
        RequestManager.addRequest(request, this);
    }
}
