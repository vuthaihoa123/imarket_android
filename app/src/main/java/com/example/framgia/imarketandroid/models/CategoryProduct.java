package com.example.framgia.imarketandroid.models;

/**
 * Created by VULAN on 7/21/2016.
 */
public class CategoryProduct {

    private int mId;
    private String mCategoryName;
    private String mUrl;

    public CategoryProduct(int id, String categoryName, String url) {
        this.mId = id;
        this.mCategoryName = categoryName;
        this.mUrl = url;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.mCategoryName = categoryName;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
