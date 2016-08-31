package com.example.framgia.imarketandroid.data.model;

import android.database.Cursor;

import io.realm.RealmObject;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Edge extends RealmObject {
    private String mNameStart;
    private String mNameEnd;
    private float mEdge;

    public Edge() {
    }

    public Edge(String nameStart, String nameEnd, float edge) {
        this.mNameStart = nameStart;
        this.mNameEnd = nameEnd;
        this.mEdge= edge;
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
        return mEdge;
    }

    public void setDistance(float edge) {
        this.mEdge = edge;
    }

    public String getSource() {
        return mNameStart;
    }

    public String getDestination() {
        return mNameEnd;
    }

    public float getWeigth() {
        return mEdge;
    }
}