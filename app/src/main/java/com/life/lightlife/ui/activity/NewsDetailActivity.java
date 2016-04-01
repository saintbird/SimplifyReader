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

package com.life.lightlife.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.obsessive.library.eventbus.EventCenter;
import com.github.obsessive.library.netstatus.NetUtils;
import com.life.lightlife.R;
import com.life.lightlife.ui.activity.base.BaseActivity;
import com.life.lightlife.utils.VolleyHelper;

import org.json.JSONObject;

import butterknife.InjectView;


public class NewsDetailActivity extends BaseActivity {

    public static final String INTENT_NEWS_URL_TAG = "INTENT_NEWS_URL_TAG";
    public static final String INTENT_NEWS_TITLE_TAG = "INTENT_NEWS_TITLE_TAG";
    public static final String INTENT_NEWS_AUTHOR_TAG = "INTENT_NEWS_AUTHOR_TAG";

    private String mNewsUrl;
    private String mNewsTitle;
    private String mNewsAuthor;

    @InjectView(R.id.webView)
    WebView mWebView;


    @Override
    protected void getBundleExtras(Bundle extras) {
        mNewsUrl = extras.getString(INTENT_NEWS_URL_TAG);
        mNewsTitle = extras.getString(INTENT_NEWS_TITLE_TAG);
        mNewsAuthor = extras.getString(INTENT_NEWS_AUTHOR_TAG);
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_news_detail;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private static String getHtml(String content, String title, String author) {
        final StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>");
        sb.append("<html dir=\"ltr\" lang=\"zh\">");
        sb.append("<head>");
        sb.append("<meta name=\"viewport\" content=\"width=100%; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" />");
        sb.append("<link rel=\"stylesheet\" href='file:///android_asset/style.css' type=\"text/css\" media=\"screen\" />");
        sb.append("</head>");
        sb.append("<body style=\"padding:0px 8px 8px 8px;\">");
        sb.append("<div id=\"pagewrapper\">");
        sb.append("<div id=\"mainwrapper\" class=\"clearfix\">");
        sb.append("<div id=\"maincontent\">");
        sb.append("<div class=\"post\">");
        sb.append("<div class=\"posthit\">");
        sb.append("<div class=\"postinfo\">");
        sb.append("<h2 class=\"thetitle\">");
        sb.append("<a>");
        sb.append(title);
        sb.append("</a>");
        sb.append("</h2>");
        sb.append(author);
        sb.append("</div>");
        sb.append("<div class=\"entry\">");
        sb.append(content);
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("</body>");
        sb.append("</html>");
        return sb.toString();
    }


    @Override
    protected void initViewsAndEvents() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(mNewsUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.opt("status").equals("ok")) {
                            JSONObject contentObject = response.optJSONObject("post");
                            String content = contentObject.optString("content");
                            int end = content.indexOf("<div class=\"share-links\">");
                            content = content.substring(0,end);
                            mWebView.loadDataWithBaseURL("", getHtml(content,mNewsTitle,mNewsAuthor), "text/html", "utf-8", "");
                        } else {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        VolleyHelper.getInstance().getRequestQueue().add(jsonObjectRequest);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
