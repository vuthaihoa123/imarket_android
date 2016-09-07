package com.example.framgia.imarketandroid.data.model;

/**
 * Created by phongtran on 07/09/2016.
 */
public class AlbumShop {
    private int mImageId;
    private String mNameAlbum;

    public AlbumShop(int imageId, String nameAlbum) {
        this.mImageId = imageId;
        this.mNameAlbum = nameAlbum;
    }

    public AlbumShop() {
    }

    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        this.mImageId = imageId;
    }

    public String getNameAlbum() {
        return mNameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        this.mNameAlbum = nameAlbum;
    }
}
