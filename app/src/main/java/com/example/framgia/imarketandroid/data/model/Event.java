package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tranphong on 07/12/2016.
 */
public class Event implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("store_id")
    private int mIdStore;
    @SerializedName("event_type")
    private int mType;
    @SerializedName("content")
    private String mContent;
    @SerializedName("name")
    private String mName;
    @SerializedName("start_event")
    private String mStartTime;
    @SerializedName("finish_event")
    private String mEndTime;

    public Event(int id, int idStore, int type, String content, String name, String startTime,
                 String endTime) {
        mId = id;
        mIdStore = idStore;
        mType = type;
        mContent = content;
        mName = name;
        mStartTime = startTime;
        mEndTime = endTime;
    }

    public Event() {
    }

    public int getId() {
        return mId;
    }

    public int getIdStore() {
        return mIdStore;
    }

    public int getType() {
        return mType;
    }

    public String getContent() {
        return mContent;
    }

    public String getName() {
        return mName;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public String getEndTime() {
        return mEndTime;
    }
}
