package com.example.framgia.imarketandroid.data.model;

import com.example.framgia.imarketandroid.R;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by framgia on 06/09/2016.
 */
public class Store extends RealmObject {
    public Store() {
    }

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("store_code")
    private String mStoreCode;
    @SerializedName("image")
    private int mAvatar;
    @SerializedName("commerce_center_id")
    private int mCommerceId;
    @SerializedName("store_type_id")
    private int mStoreTypeId;
    @SerializedName("lat")
    private double mLat;
    @SerializedName("lng")
    private double mLng;

    public Store(int mId, String mName, String mStoreCode, int mAvatar, int commerceId,
                 int mStoreTypeId, double lat, double lng) {
        this.mId = mId;
        this.mName = mName;
        this.mStoreCode = mStoreCode;
        this.mAvatar = mAvatar;
        mCommerceId = commerceId;
        this.mStoreTypeId = mStoreTypeId;
        this.mLat = lat;
        this.mLng = lng;
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

    public String getStoreCode() {
        return mStoreCode;
    }

    public void setStoreCode(String mStoreCode) {
        this.mStoreCode = mStoreCode;
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

    public void setCommerceId(int commerceId) {
        mCommerceId = commerceId;
    }

    public int getmStoreTypeId() {
        return mStoreTypeId;
    }

    public void setmStoreTypeId(int mStoreTypeId) {
        this.mStoreTypeId = mStoreTypeId;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLng() {
        return mLng;
    }

    public void setmLng(double mLng) {
        this.mLng = mLng;
    }
}
