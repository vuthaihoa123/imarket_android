package com.example.framgia.imarketandroid.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toannguyen201194 on 07/09/2016.
 */
public class CategorySaleOff {
    private String mNameSaleOff;
    private List<ItemProduct> mProductList = new ArrayList<>();

    public CategorySaleOff(String nameSaleOff, List<ItemProduct> productList) {
        mNameSaleOff = nameSaleOff;
        mProductList = productList;
    }

    public String getNameSaleOff() {
        return mNameSaleOff;
    }

    public void setNameSaleOff(String nameSaleOff) {
        mNameSaleOff = nameSaleOff;
    }

    public List<ItemProduct> getProductList() {
        return mProductList;
    }

    public void setProductList(
        List<ItemProduct> productList) {
        mProductList = productList;
    }
}
