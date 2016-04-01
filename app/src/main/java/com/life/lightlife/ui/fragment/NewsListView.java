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

package com.life.lightlife.view;

import com.life.lightlife.bean.ResponseNewsListEntity;
import com.life.lightlife.bean.NewsListEntity;
import com.life.lightlife.view.base.BaseView;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/4/9.
 * Description:
 */
public interface NewsListView extends BaseView {

    void refreshListData(ResponseNewsListEntity responseNewsListEntity);

    void addMoreListData(ResponseNewsListEntity responseNewsListEntity);

    void navigateToNewsDetail(int position, NewsListEntity entity);

}
