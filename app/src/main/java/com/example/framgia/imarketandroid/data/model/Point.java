package com.example.framgia.imarketandroid.data.model;

import android.database.Cursor;

import io.realm.RealmObject;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Point extends RealmObject {
    private int mId;
    private int mType;
    private double mLat, mLng;
    private String mName;

    public Point(int id, double lat, double lng, int type, String name) {
        this.mId = id;
        this.mLat = lat;
        this.mLng = lng;
        this.mType = type;
        this.mName = name;
    }

    public Point() {
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        this.mLng = lng;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        this.mLat = lat;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mId == -1) ? 0 : mId);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (mId == -1) {
            if (other.mId != -1)
                return false;
        } else if (mId != other.mId)
            return false;
        return true;
    }
}
