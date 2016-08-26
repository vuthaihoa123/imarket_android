package com.example.framgia.imarketandroid.data.DataObject;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by framgia on 24/08/2016.
 */
public class Graph extends RealmObject {
    private RealmList<Edge> mEdges;
    private RealmList<Point> mVertexes;

    public Graph(){}
    public Graph(RealmList<Point> vertexes, RealmList<Edge> edges) {
        mVertexes = vertexes;
        mEdges = edges;
    }

    public List<Point> getVertexes() {
        return mVertexes;
    }

    public List<Edge> getEdges() {
        return mEdges;
    }
}
