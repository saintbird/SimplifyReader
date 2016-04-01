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

package com.life.lightlife.interactor.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.github.obsessive.library.utils.TLog;
import com.life.lightlife.bean.ResponseNewsListEntity;
import com.life.lightlife.interactor.CommonListInteractor;
import com.life.lightlife.listeners.BaseMultiLoadedListener;
import com.life.lightlife.utils.UriHelper;
import com.life.lightlife.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/4/9.
 * Description:
 */
public class NewsListInteractorImpl implements CommonListInteractor {

    private BaseMultiLoadedListener<ResponseNewsListEntity> loadedListener = null;

    public NewsListInteractorImpl(BaseMultiLoadedListener<ResponseNewsListEntity> loadedListener) {
        this.loadedListener = loadedListener;
    }

    @Override
    public void getCommonListData(final String requestTag, final int event_tag, String keywords, int page) {
        TLog.d(requestTag, UriHelper.getInstance().getNewsListUrl(page));

        GsonRequest<ResponseNewsListEntity> gsonRequest = new GsonRequest<ResponseNewsListEntity>(
                //UriHelper.getInstance().getVideosListUrl(keywords, page),
                UriHelper.getInstance().getNewsListUrl(page),
                null,
                new TypeToken<ResponseNewsListEntity>() {
                }.getType(),
                new Response.Listener<ResponseNewsListEntity>() {
                    @Override
                    public void onResponse(ResponseNewsListEntity response) {
                        loadedListener.onSuccess(event_tag, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadedListener.onException(error.getMessage());
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag(requestTag);

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
