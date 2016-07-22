package com.example.framgia.imarketandroid.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class Floor {
    private int mIdFloor;
    private String mNameFloor;
    private List<Shop> mShopList;

    public Floor(int idFloor, String nameFloor) {
        mIdFloor = idFloor;
        mNameFloor = nameFloor;
        mShopList =new ArrayList<Shop>();
    }

    public int getIdFloor() {
        return mIdFloor;
    }

    public void setIdFloor(int idFloor) {
        mIdFloor = idFloor;
    }

    public String getNameFloor() {
        return mNameFloor;
    }

    public void setNameFloor(String nameFloor) {
        mNameFloor = nameFloor;
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
        return mNameFloor;
    }
}
