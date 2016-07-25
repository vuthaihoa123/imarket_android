package com.example.framgia.imarketandroid.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class Floor {
    private int mId;
    private String mName;
    private List<Shop> mShopList;

    public Floor(int idFloor, String nameFloor) {
        mId = idFloor;
        mName = nameFloor;
        mShopList =new ArrayList<Shop>();
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

    public List<Shop> getShopList() {
        return mShopList;
    }

    public void setShopList(List<Shop> shopList) {
        mShopList.clear();
        mShopList.addAll(shopList);
    }

    @Override
    public String toString() {
        return mName;
    }
}
