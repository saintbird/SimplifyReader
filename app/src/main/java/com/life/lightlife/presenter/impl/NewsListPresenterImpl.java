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

package com.life.lightlife.presenter.impl;

import android.content.Context;

import com.life.lightlife.R;
import com.life.lightlife.bean.ResponseNewsListEntity;
import com.life.lightlife.bean.NewsListEntity;
import com.life.lightlife.common.Constants;
import com.life.lightlife.interactor.CommonListInteractor;
import com.life.lightlife.interactor.impl.NewsListInteractorImpl;
import com.life.lightlife.listeners.BaseMultiLoadedListener;
import com.life.lightlife.presenter.NewsListPresenter;
import com.life.lightlife.view.NewsListView;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/4/9.
 * Description:
 */
public class NewsListPresenterImpl implements NewsListPresenter, BaseMultiLoadedListener<ResponseNewsListEntity> {

    private Context mContext = null;
    private NewsListView mNewsListView = null;
    private CommonListInteractor mCommonListInteractor = null;

    public NewsListPresenterImpl(Context context, NewsListView newsListView) {
        mContext = context;
        mNewsListView = newsListView;
        mCommonListInteractor = new NewsListInteractorImpl(this);
    }

    @Override
    public void onSuccess(int event_tag, ResponseNewsListEntity data) {
        mNewsListView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mNewsListView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mNewsListView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mNewsListView.hideLoading();
        mNewsListView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mNewsListView.hideLoading();
        mNewsListView.showError(msg);
    }

    @Override
    public void loadListData(String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh) {
        mNewsListView.hideLoading();
        if (!isSwipeRefresh) {
            mNewsListView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        mCommonListInteractor.getCommonListData(requestTag, event_tag, keywords, page);
    }

    @Override
    public void onItemClickListener(int position, NewsListEntity entity) {
        mNewsListView.navigateToNewsDetail(position, entity);
    }
}