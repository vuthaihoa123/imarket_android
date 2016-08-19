package com.example.framgia.imarketandroid.models;

/**
 * Created by hoavt on 18/08/2016.
 */
public class CartItem {
    private int mIdRes = -1;
    private String mNameProduct;
    private long mPriceProduct;
    private int mQuantity = 1;
    private boolean mIsDeleted = false;

    public CartItem(int idRes, String nameProduct, long priceProduct, int quantity, boolean isDeleted) {
        mIdRes = idRes;
        mNameProduct = nameProduct;
        mQuantity = quantity;
        mIsDeleted = isDeleted;
        mPriceProduct = priceProduct;
    }

    public int getIdRes() {
        return mIdRes;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public long getPriceProduct() {
        return mPriceProduct;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public boolean isDeleted() {
        return mIsDeleted;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void setIsDeleted(boolean isDeleted) {
        mIsDeleted = isDeleted;
    }
}
