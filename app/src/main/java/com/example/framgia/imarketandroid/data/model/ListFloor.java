package com.example.framgia.imarketandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phongtran on 07/10/2016.
 */
public class ListFloor {
    @SerializedName("floors")
    private List<Floor> mFloorList = new ArrayList<>();

    public ListFloor(List<Floor> floorList) {
        this.mFloorList = floorList;
    }

    public ListFloor() {
    }

    public List<Floor> getFloorList() {
        return mFloorList;
    }

    public void setFloorList(List<Floor> floorList) {
        this.mFloorList = floorList;
    }
}
