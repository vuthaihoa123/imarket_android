package com.example.framgia.imarketandroid.data.model;

/**
 * Created by framgia on 06/09/2016.
 */
public class Store {
    private int mAvatar;
    private String mName;

    public Store(int food_avatar, String name) {
        this.mAvatar= food_avatar;
        this.mName= name;
    }

    public int getAvatar() {
        return mAvatar;
    }

    public void setAvatar(int mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
