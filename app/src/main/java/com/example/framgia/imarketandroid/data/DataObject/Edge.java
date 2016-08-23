package com.example.framgia.imarketandroid.data.DataObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by framgia on 23/08/2016.
 */
public class Edge extends RealmObject {
    private String nameStart;
    private String nameEnd;
    private float edge;
    public Edge(){

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
