package com.example.framgia.imarketandroid.data.model;

/**
 * Created by hoavt on 20/07/2016.
 */
public class ItemProduct {
    private String mNameProduct;
    private String mPromotionPercent;
    private int mPresentIcon;
    private String mPricePromotion;
    private String mPrice;

    public ItemProduct(String nameProduct, String promotionPercent, int presentIcon,
                       String pricePromotion, String price) {
        mNameProduct = nameProduct;
        mPromotionPercent = promotionPercent;
        mPresentIcon = presentIcon;
        mPricePromotion = pricePromotion;
        mPrice = price;
    }

    public ItemProduct(String nameProduct, String promotionPercent, int presentIcon) {
        mNameProduct = nameProduct;
        mPromotionPercent = promotionPercent;
        mPresentIcon = presentIcon;
    }

    public String getPricePromotion() {
        return mPricePromotion;
    }

    public void setPricePromotion(String pricePromotion) {
        mPricePromotion = pricePromotion;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getNameProduct() {
        return mNameProduct;
    }

    public void setNameProduct(String nameProduct) {
        mNameProduct = nameProduct;
    }

    public String getPromotionPercent() {
        return mPromotionPercent;
    }

    public void setPromotionPercent(String promotionPercent) {
        mPromotionPercent = promotionPercent;
    }

    public int getPresentIcon() {
        return mPresentIcon;
    }

    public void setPresentIcon(int presentIcon) {
        mPresentIcon = presentIcon;
    }
}
