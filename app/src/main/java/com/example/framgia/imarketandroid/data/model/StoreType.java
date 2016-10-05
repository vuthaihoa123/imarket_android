package com.example.framgia.imarketandroid.data.model;

import com.example.framgia.imarketandroid.data.remote.RealmRemote;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by framgia on 03/10/2016.
 */
public class StoreType extends RealmObject {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("avatar")
    private int mAvatar;
    @SerializedName("id_commerce")
    private int mCommerceId;

    public StoreType() {
    }

    public StoreType(int mId, String mName, int mAvatar, int mCommerceId) {
        this.mId = mId;
        this.mName = mName;
        this.mAvatar = mAvatar;
        this.mCommerceId = mCommerceId;
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

    public int getAvatar() {
        return mAvatar;
    }

    public void setAvatar(int mAvatar) {
        this.mAvatar = mAvatar;
    }

    public int getCommerceId() {
        return mCommerceId;
    }

    public void setCommerceId(int mCommerceId) {
        this.mCommerceId = mCommerceId;
    }
}
