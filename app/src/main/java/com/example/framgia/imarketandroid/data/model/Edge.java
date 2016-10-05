package com.example.framgia.imarketandroid.data.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Edge extends RealmObject {
    @SerializedName("name_start")
    private String mNameStart;
    @SerializedName("name_end")
    private String mNameEnd;
    @SerializedName("length_edge")
    private float mLengthEdge;

    public Edge() {
    }

    public Edge(String nameStart, String nameEnd, float edge) {
        this.mNameStart = nameStart;
        this.mNameEnd = nameEnd;
        this.mLengthEdge = edge;
    }

    public String getNameStart() {
        return mNameStart;
    }

    public void setIdStart(String nameStart) {
        this.mNameStart = nameStart;
    }

    public String getNameEnd() {
        return mNameEnd;
    }

    public void setNameEnd(String nameEnd) {
        this.mNameEnd = nameEnd;
    }

    public float getDistance() {
        return mLengthEdge;
    }

    public void setDistance(float edge) {
        this.mLengthEdge = edge;
    }

    public String getSource() {
        return mNameStart;
    }

    public String getDestination() {
        return mNameEnd;
    }

    public float getWeigth() {
        return mLengthEdge;
    }
}