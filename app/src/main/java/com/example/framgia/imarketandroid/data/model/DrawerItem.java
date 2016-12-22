package com.example.framgia.imarketandroid.data.model;

/**
 * Created by yue on 31/07/2016.
 */
public class DrawerItem {
    private String mTitle;
    private String mImage;
    private boolean mIsTail;

    public DrawerItem() {
        this("", "", false);
    }

    public DrawerItem(String title, String image) {
        this(title, image, false);
    }

    public DrawerItem(String title, String image, boolean isTail) {
        this.mTitle = title;
        this.mImage = image;
        this.mIsTail = isTail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public boolean isTail() {
        return mIsTail;
    }
}
