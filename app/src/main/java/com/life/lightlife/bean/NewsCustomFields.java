package com.life.lightlife.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jeffery on 16/3/25.
 */
public class NewsCustomFields implements Parcelable {
    private List<String> thumb_c;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumb_c.get(0));
    }

    public NewsCustomFields() {
    }

    protected NewsCustomFields(Parcel in) {
        this.thumb_c.set(0,in.readString());
    }

    public static final Parcelable.Creator<NewsCustomFields> CREATOR = new Parcelable.Creator<NewsCustomFields>() {
        public NewsCustomFields createFromParcel(Parcel source) {
            return new NewsCustomFields(source);
        }

        public NewsCustomFields[] newArray(int size) {
            return new NewsCustomFields[size];
        }
    };

    public String getThumb_c() {
        String thumb_url = "";
        thumb_url = thumb_c.get(0);

        if (thumb_url.contains("custom")) {
            thumb_url = thumb_url.replace("custom", "medium");
        }
        return thumb_url;
    }

    public void setThumb_c(String thumb_c) {
        this.thumb_c.set(0,thumb_c);
    }
}
