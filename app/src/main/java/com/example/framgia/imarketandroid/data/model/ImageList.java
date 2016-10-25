package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VULAN on 10/24/2016.
 */

public class ImageList {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("photo")
    private String mPhotoLink;

    public ImageList(int mId, String mPhotoLink, String mName) {
        this.mId = mId;
        this.mPhotoLink = mPhotoLink;
        this.mName = mName;
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

    public String getPhotoLink() {
        return mPhotoLink;
    }

    public void setPhotoLink(String mPhotoLink) {
        this.mPhotoLink = mPhotoLink;
    }
}
