package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by framgia on 03/10/2016.
 */
public class StoreTypeList {
    @SerializedName("store_types")
    private List<StoreType> mListStoreType = new ArrayList<>();

    public List<StoreType> getListStoreType() {
        return mListStoreType;
    }
}