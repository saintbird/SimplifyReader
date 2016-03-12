package com.life.lightlife.jiandan.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.life.lightlife.R;
import com.life.lightlife.jiandan.adapter.FreshNewsAdapter;
import com.life.lightlife.jiandan.base.BaseFragment;
import com.life.lightlife.jiandan.base.ConstantString;
import com.life.lightlife.jiandan.callback.LoadMoreListener;
import com.life.lightlife.jiandan.callback.LoadResultCallBack;
import com.life.lightlife.jiandan.utils.ShowToast;
import com.life.lightlife.jiandan.view.AutoLoadRecyclerView;
import com.victor.loading.rotate.RotateLoading;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FreshNewsFragment extends BaseFragment implements LoadResultCallBack {

    @InjectView(R.id.recycler_view)
    AutoLoadRecyclerView mRecyclerView;
    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.loading)
    RotateLoading loading;

    private FreshNewsAdapter mAdapter;

    public FreshNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto_load, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLoadMoreListener(new LoadMoreListener() {
            @Override
            public void loadMore() {
                mAdapter.loadNextPage();
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.loadFirst();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnPauseListenerParams(false, true);

        mAdapter = new FreshNewsAdapter(getActivity(), mRecyclerView, this, false);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.loadFirst();
        loading.start();
    }

    @Override
    public void onSuccess(int result, Object object) {
        loading.stop();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onError(int code, String msg) {
        loading.stop();
        ShowToast.Short(ConstantString.LOAD_FAILED);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}