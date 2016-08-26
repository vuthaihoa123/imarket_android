package com.example.framgia.imarketandroid.data.model;

import android.database.Cursor;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Edge {
    private String nameStart;
    private String nameEnd;
    private float edge;
    public Edge(Cursor cursor){
        this.nameStart = cursor.getString(cursor.getColumnIndex("id_start"));
        this.nameEnd = cursor.getString(cursor.getColumnIndex("id_end"));
        this.edge= cursor.getFloat(cursor.getColumnIndex("edge"));
    }
    public Edge(String nameStart, String nameEnd, float edge) {
        this.nameStart = nameStart;
        this.nameEnd = nameEnd;
        this.edge= edge;
    }

    public String getNameStart() {
        return nameStart;
    }

    public void setIdStart(String nameStart) {
        this.nameStart = nameStart;
    }

    public String getNameEnd() {
        return nameEnd;
    }

    public void setNameEnd(String nameEnd) {
        this.nameEnd = nameEnd;
    }

    public float getDistance() {
        return edge;
    }

    public void setDistance(float edge) {
        edge = edge;
    }

    public String getSource() {
        return nameStart;
    }

    public String getDestination() {
        return nameEnd;
    }

    public float getWeigth() {
        return edge;
    }
}