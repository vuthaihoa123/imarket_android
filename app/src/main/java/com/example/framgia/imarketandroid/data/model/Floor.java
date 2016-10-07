package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class Floor {
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("commerce_center_id")
    private int mCommerceId;

    public Floor() {
    }

    public int getIdFloor() {
        return mId;
    }

    public void setIdFloor(int idFloor) {
        mId = idFloor;
    }

    public String getNameFloor() {
        return mName;
    }

    public void setNameFloor(String nameFloor) {
        mName = nameFloor;
    }

    @Override
    public String toString() {
        return mName;
    }
}
