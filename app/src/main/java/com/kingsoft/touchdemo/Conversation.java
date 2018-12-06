package com.kingsoft.touchdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/12/6.
 */

public class Conversation implements Parcelable {

    public String id;
    public String name;

    public Conversation(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public Conversation() {
    }

    protected Conversation(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Conversation> CREATOR = new Parcelable.Creator<Conversation>() {
        @Override
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        @Override
        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
