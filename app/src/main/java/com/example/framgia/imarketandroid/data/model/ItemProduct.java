package com.example.framgia.imarketandroid.data.model;

/**
 * Created by hoavt on 20/07/2016.
 */
public class ItemProduct {
    private String mNameProduct;
    private String mPromotionPercent;
    private int mPresentIcon;

    public ItemProduct(String nameProduct, String promotionPercent, int presentIcon) {
        mNameProduct = nameProduct;
        mPromotionPercent = promotionPercent;
        mPresentIcon = presentIcon;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public String getPromotionPercent() {
        return mPromotionPercent;
    }

    public int getPresentIcon() {
        return mPresentIcon;
    }
}
