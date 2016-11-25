package com.example.framgia.imarketandroid.data.model;
import android.os.Parcel;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import io.realm.RealmObject;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Point extends RealmObject implements Serializable {
    @SerializedName("id")
    private int mId;
    @SerializedName("type")
    private int mType;
    @SerializedName("lat")
    private double mLat;
    @SerializedName("lng")
    private double mLng;
    @SerializedName("id_store")
    private int mIdStore;

    public Point(int id, int type, double lat, double lng, int IdStore) {
        this.mId = id;
        this.mLat = lat;
        this.mLng = lng;
        this.mType = type;
        this.mIdStore = IdStore;
    }

    public Point(double lat, double lng) {
        this.mLat = lat;
        this.mLng = lng;
    }

    public Point() {
    }

    protected Point(Parcel in) {
        mId = in.readInt();
        mType = in.readInt();
        mLat = in.readDouble();
        mLng = in.readDouble();
        mIdStore = in.readInt();
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

    public int getIdStore() {
        return mIdStore;
    }

    public void setIdStore(int IdStore) {
        this.mIdStore = IdStore;
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
