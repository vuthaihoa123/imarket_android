package com.example.framgia.imarketandroid.data.model;

import java.util.List;

/**
 * Created by hoavt on 07/07/2016.
 */
public class Graph {
    private List<Point> mVertexes;
    private List<Edge> mEdges;

    public Graph(List<Point> vertexes, List<Edge> edges) {
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
