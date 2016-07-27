package com.example.framgia.imarketandroid.data.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by VULAN on 7/26/2016.
 */
public class CustomMarker implements ClusterItem {
    private LatLng mPosition;
    private int mId;
    private double mLatitude;
    private double mLongitude;
    private double mNumber;
    private Category mCategory;

    public CustomMarker(int id, double latitude, double longitude, double number, Category category) {
        this.mId = id;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mNumber = number;
        this.mCategory = category;
        mPosition = new LatLng(latitude, longitude);
    }

    public int getId() {
        return mId;
    }

    public void setId(int Id) {
        this.mId = Id;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double Latitude) {
        this.mLatitude = Latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double Longitude) {
        this.mLongitude = Longitude;
    }

    public double getNumber() {
        return mNumber;
    }

    public void setNumber(double Number) {
        this.mNumber = Number;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category mCategory) {
        this.mCategory = mCategory;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
