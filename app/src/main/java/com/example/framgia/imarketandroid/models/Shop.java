package com.example.framgia.imarketandroid.models;

/**
 * Created by toannguyen201194 on 19/07/2016.
 */
public class Shop {
    private int mIdShop;
    private String mNameShop;

    public Shop(int idShop, String nameShop) {
        mIdShop = idShop;
        mNameShop = nameShop;
    }

    public int getIdShop() {
        return mIdShop;
    }

    public void setIdShop(int idShop) {
        mIdShop = idShop;
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
