package com.example.framgia.imarketandroid.data.model;

import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by framgia on 03/10/2016.
 */
public class StoreType {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("id_commerce")
    private int mCommerceId;

    public StoreType() {
    }

    public StoreType(int id, String name, String avatar, int commerceId) {
        this.mId = id;
        this.mName = name;
        this.mAvatar = avatar;
        this.mCommerceId = commerceId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public int getCommerceId() {
        return mCommerceId;
    }

    public void setCommerceId(int mCommerceId) {
        this.mCommerceId = mCommerceId;
    }
}