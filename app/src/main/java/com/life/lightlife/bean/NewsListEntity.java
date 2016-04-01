package com.life.lightlife.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 新鲜事
 * Created by zhaokaiqiang on 15/4/24.
 */
public class NewsListEntity implements Parcelable {

    //文章id
    private String id;
    //文章标题
    private String title;
    //文章地址
    private String url;
    //发布日期
    private String date;

    private NewsAuthor author;

    private NewsCustomFields custom_fields;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeString(this.date);
    }

    public NewsListEntity() {
    }

    protected NewsListEntity(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.url = in.readString();
        this.date = in.readString();

    }

    public static final Parcelable.Creator<NewsListEntity> CREATOR = new Parcelable.Creator<NewsListEntity>() {
        public NewsListEntity createFromParcel(Parcel source) {
            return new NewsListEntity(source);
        }

        public NewsListEntity[] newArray(int size) {
            return new NewsListEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public NewsCustomFields getCustomFields() {
        return custom_fields;
    }

    public void setCustomFields(NewsCustomFields customFields) {
        this.custom_fields = customFields;
    }

    public NewsAuthor getAuthor(){ return author;}

    public void setAuthor(NewsAuthor newsAuthor){
        this.author = newsAuthor;
    }

}
