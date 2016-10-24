package com.example.framgia.imarketandroid.data.model;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Edge extends RealmObject {
    @SerializedName("name_start")
    private int mNameStart;
    @SerializedName("name_end")
    private int mNameEnd;
    @SerializedName("length_edge")
    private float mLengthEdge;

    public Edge() {
    }

    public Edge(int nameStart, int nameEnd, float edge) {
        this.mNameStart = nameStart;
        this.mNameEnd = nameEnd;
        this.mLengthEdge = edge;
    }

    public int getNameStart() {
        return mNameStart;
    }

    public void setIdStart(int nameStart) {
        this.mNameStart = nameStart;
    }

    public int getNameEnd() {
        return mNameEnd;
    }

    public void setNameEnd(int nameEnd) {
        this.mNameEnd = nameEnd;
    }

    public float getDistance() {
        return mLengthEdge;
    }

    public void setDistance(float edge) {
        this.mLengthEdge = edge;
    }

    public int getSource() {
        return mNameStart;
    }

    public int getDestination() {
        return mNameEnd;
    }

    public float getWeigth() {
        return mLengthEdge;
    }
}