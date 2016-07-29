package com.example.framgia.imarketandroid.models;

/**
 * Created by hoavt on 22/07/2016.
 */
public class ItemBooking {
    private int mIdResIcon;
    private String mDescribe;

    public ItemBooking(int idResIcon, String describe) {
        mIdResIcon = idResIcon;
        mDescribe = describe;
    }

    public int getIdResIcon() {
        return mIdResIcon;
    }

    public String getDescribe() {
        return mDescribe;
    }
}
