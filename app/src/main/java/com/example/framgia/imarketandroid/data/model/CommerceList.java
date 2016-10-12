package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 10/10/2016.
 */
public class CommerceList {
    @SerializedName("commerce_centers")
    private List<CommerceCanter> mCommerceCenterList = new ArrayList<>();

    public CommerceList(
        List<CommerceCanter> centerList) {
        this.mCommerceCenterList = centerList;
    }

    public CommerceList() {
    }

    public List<CommerceCanter> getCenterList() {
        return mCommerceCenterList;
    }

    public void setCenterList(
        List<CommerceCanter> centerList) {
        this.mCommerceCenterList = centerList;
    }
}
