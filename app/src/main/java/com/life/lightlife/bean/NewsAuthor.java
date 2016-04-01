package com.life.lightlife.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class NewsAuthor implements Parcelable {

    public String id;
    public String slug;
    public String name;
    public String first_name;
    public String last_name;
    public String nickname;
    public String url;
    public String description;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.slug);
        dest.writeString(this.name);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.nickname);
        dest.writeString(this.url);
        dest.writeString(this.description);
    }

    public NewsAuthor() {
    }

    protected NewsAuthor(Parcel in) {
        this.id = in.readString();
        this.slug = in.readString();
        this.name = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.nickname = in.readString();
        this.url = in.readString();
        this.description = in.readString();

    }

    public static final Parcelable.Creator<NewsAuthor> CREATOR = new Parcelable.Creator<NewsAuthor>() {
        public NewsAuthor createFromParcel(Parcel source) {
            return new NewsAuthor(source);
        }

        public NewsAuthor[] newArray(int size) {
            return new NewsAuthor[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
