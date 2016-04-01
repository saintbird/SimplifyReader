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

package com.life.lightlife.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.obsessive.library.adapter.ListViewDataAdapter;
import com.github.obsessive.library.adapter.MultiItemRowListAdapter;
import com.github.obsessive.library.adapter.ViewHolderBase;
import com.github.obsessive.library.adapter.ViewHolderCreator;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.github.obsessive.library.utils.CommonUtils;
import com.github.obsessive.library.utils.TLog;
import com.github.obsessive.library.widgets.XSwipeRefreshLayout;
import com.life.lightlife.R;
import com.life.lightlife.api.ApiConstants;
import com.life.lightlife.bean.ResponseNewsListEntity;
import com.life.lightlife.bean.NewsListEntity;
import com.life.lightlife.common.Constants;
import com.life.lightlife.common.OnCommonPageSelectedListener;
import com.life.lightlife.presenter.NewsListPresenter;
import com.life.lightlife.ui.activity.NewsDetailActivity;
import com.life.lightlife.presenter.impl.NewsListPresenterImpl;
import com.life.lightlife.ui.activity.base.BaseFragment;
import com.life.lightlife.utils.UriHelper;
import com.life.lightlife.view.NewsListView;
import com.life.lightlife.widgets.LoadMoreListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/4/9.
 * Description:
 */
public class NewsListFragment extends BaseFragment implements NewsListView, OnCommonPageSelectedListener, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @InjectView(R.id.fragment_news_list_swipe_layout)
    XSwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.fragment_videos_list_list_view)
    LoadMoreListView mListView;

    /**
     * this variable must be initialized.
     */
    private static String mCurrentVideosCategory = null;
    /**
     * the page number
     */
    private int mCurrentPage = 1;

    private NewsListPresenter mNewsListPresenter = null;

    private MultiItemRowListAdapter mMultiItemRowListAdapter = null;
    private ListViewDataAdapter<NewsListEntity> mListViewAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentVideosCategory = "0";
    }

    @Override
    protected void onFirstUserVisible() {
        mCurrentPage = 1;
        mNewsListPresenter = new NewsListPresenterImpl(mContext, this);

        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != mSwipeRefreshLayout) {
                mSwipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentVideosCategory,
                                mCurrentPage, false);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentVideosCategory,
                            mCurrentPage, false);
                }
            });
        }
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return mSwipeRefreshLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        mListViewAdapter = new ListViewDataAdapter<NewsListEntity>(new ViewHolderCreator<NewsListEntity>() {
            @Override
            public ViewHolderBase<NewsListEntity> createViewHolder(int position) {
                return new ViewHolderBase<NewsListEntity>() {

                    TextView mItemTitle;
                    ImageView mItemImage;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View convertView = layoutInflater.inflate(R.layout.list_item_news_card, null);

                        mItemTitle = ButterKnife.findById(convertView, R.id.list_item_videos_card_title);
                        mItemImage = ButterKnife.findById(convertView, R.id.list_item_videos_card_image);

                        return convertView;
                    }

                    @Override
                    public void showData(final int position, final NewsListEntity itemData) {
                        if (null != itemData) {
                            if (!CommonUtils.isEmpty(itemData.getTitle())) {
                                mItemTitle.setText(CommonUtils.decodeUnicodeStr(itemData.getTitle()));
                            }

                            if (!CommonUtils.isEmpty(itemData.getCustomFields().getThumb_c())) {
                                ImageLoader.getInstance().displayImage(itemData.getCustomFields().getThumb_c(), mItemImage);
                            }

                            mItemImage.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, NewsDetailActivity.class);
                                    intent.putExtra(NewsDetailActivity.INTENT_NEWS_URL_TAG, UriHelper.getNewsDetailUrl(itemData.getId()));
                                    intent.putExtra(NewsDetailActivity.INTENT_NEWS_TITLE_TAG, itemData.getTitle());
                                    intent.putExtra(NewsDetailActivity.INTENT_NEWS_AUTHOR_TAG, itemData.getAuthor().getName());
                                    mContext.startActivity(intent);
                                }
                            });
                        }
                    }
                };
            }
        });

        mMultiItemRowListAdapter = new MultiItemRowListAdapter(mContext, mListViewAdapter, 1, 0);

        mListView.setAdapter(mMultiItemRowListAdapter);
        mListView.setOnLoadMoreListener(this);

        mSwipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onPageSelected(int position, String category) {
        mCurrentVideosCategory = category;
    }

    @Override
    public void showError(String msg) {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        toggleShowError(true, msg, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentVideosCategory,
                        mCurrentPage, false);
            }
        });
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 1;
        mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_REFRESH_DATA, mCurrentVideosCategory, mCurrentPage,
                true);
    }

    @Override
    public void onLoadMore() {
        mCurrentPage++;
        mNewsListPresenter.loadListData(TAG_LOG, Constants.EVENT_LOAD_MORE_DATA, mCurrentVideosCategory, mCurrentPage, true);
    }

    @Override
    public void refreshListData(ResponseNewsListEntity responseNewsListEntity) {
        if (null != mSwipeRefreshLayout) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

        if (null != responseNewsListEntity && null != responseNewsListEntity.getNews() && !responseNewsListEntity.getNews().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.getDataList().addAll(responseNewsListEntity.getNews());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (UriHelper.getInstance().calculateTotalPages(responseNewsListEntity.getTotal()) > mCurrentPage) {
                mListView.setCanLoadMore(true);
            } else {
                mListView.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void addMoreListData(ResponseNewsListEntity responseNewsListEntity) {
        if (null != mListView) {
            mListView.onLoadMoreComplete();
        }

        if (null != responseNewsListEntity && null != responseNewsListEntity.getNews() && !responseNewsListEntity.getNews().isEmpty()) {
            if (null != mListViewAdapter) {
                mListViewAdapter.getDataList().addAll(responseNewsListEntity.getNews());
                mListViewAdapter.notifyDataSetChanged();
            }

            if (UriHelper.getInstance().calculateTotalPages(responseNewsListEntity.getTotal()) > mCurrentPage) {
                mListView.setCanLoadMore(true);
            } else {
                mListView.setCanLoadMore(false);
            }
        }
    }

    @Override
    public void navigateToNewsDetail(int position, NewsListEntity entity) {
        Bundle extras = new Bundle();
        TLog.v("news","news click");
        //extras.putParcelable(PlayerActivity.INTENT_VIDEO_EXTRAS, entity);
        //readyGo(PlayerActivity.class, extras);
    }
}
