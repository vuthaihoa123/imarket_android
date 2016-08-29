package com.example.framgia.imarketandroid.data.model;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class Shop {
    private int mId;
    private String mNameShop;

    public Shop(int idShop, String nameShop) {
        mId = idShop;
        mNameShop = nameShop;
    }

    public int getIdShop() {
        return mId;
    }

    public void setIdShop(int idShop) {
        mId = idShop;
    }

    public String getNameShop() {
        return mNameShop;
    }

    public void setNameShop(String nameShop) {
        mNameShop = nameShop;
    }

    @Override
    public String toString() {
        return mNameShop;
    }
}
