package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by phongtran on 10/10/2016.
 */
public class CommerceCanter extends RealmObject implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("image")
    private String mImage;

    public CommerceCanter(int id, String name, String address, String image) {
        this.mId = id;
        this.mName = name;
        this.mAddress = address;
        this.mImage = image;
    }

    public CommerceCanter() {
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = mName;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }
}
