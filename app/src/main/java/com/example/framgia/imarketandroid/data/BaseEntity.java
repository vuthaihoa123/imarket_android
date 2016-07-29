package com.example.framgia.imarketandroid.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VULAN on 7/29/2016.
 */
public class BaseEntity {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    public BaseEntity() {
    }

    public BaseEntity(String id, String name) {
        mId = id;
        mName = name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
