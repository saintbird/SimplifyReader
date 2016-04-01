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

package com.life.lightlife.bean;

import java.util.List;

/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    2015/4/9.
 * Description:
 */
public class ResponseNewsListEntity {
    private int count_total;
    private List<NewsListEntity> posts;

    public int getTotal() {
        return count_total;
    }

    public void setTotal(int total) {
        this.count_total = total;
    }

    public List<NewsListEntity> getNews() {
        return posts;
    }

    public void setNews(List<NewsListEntity> posts) {
        this.posts = posts;
    }
}
