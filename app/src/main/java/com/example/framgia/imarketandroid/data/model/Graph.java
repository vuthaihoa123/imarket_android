package com.example.framgia.imarketandroid.data.model;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by hoavt on 07/07/2016.
 */
public class Graph {
    private RealmList<Point> mVertexes;
    private RealmList<Edge> mEdges;

    public Graph(RealmList<Point> vertexes,
                 RealmList<Edge> edges) {
        mVertexes = vertexes;
        mEdges = edges;
    }

    public RealmList<Point> getVertexes() {
        return mVertexes;
    }

    public RealmList<Edge> getEdges() {
        return mEdges;
    }
}
