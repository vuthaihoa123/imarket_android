package com.example.framgia.imarketandroid.data.model;

/**
 * Created by phongtran on 25/11/2016.
 */
public class NearMarket {
    private CommerceCanter mCenter;
    private float mDistance;

    public NearMarket(CommerceCanter center, float distance) {
        mCenter = center;
        mDistance = distance;
    }

    public NearMarket() {
    }

    public CommerceCanter getCenter() {
        return mCenter;
    }

    public void setCenter(CommerceCanter center) {
        mCenter = center;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setDistance(float distance) {
        mDistance = distance;
    }
}
