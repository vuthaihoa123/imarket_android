package com.example.framgia.imarketandroid.util.algorithm;

import android.content.Context;
import android.util.Log;

import com.example.framgia.imarketandroid.data.DataObject.Edge;
import com.example.framgia.imarketandroid.data.DataObject.Graph;
import com.example.framgia.imarketandroid.data.DataObject.Point;
import com.example.framgia.imarketandroid.data.remote.DatabaseRemote;
import com.example.framgia.imarketandroid.data.remote.RealmRemote;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hoavt on 07/07/2016.
 */
public class DijkstraAlgorithm {
    private static final String TAG = "DijkstraAlgorithm";
    private List<Point> mNodes;
    private List<Edge> mEdges;
    private Set<Point> mSettledNodes;
    private Set<Point> mUnsettledNodes;
    private Map<Point, Point> mPredecessors;
    private Map<Point, Float> mDistance;
    private Context context;
    private RealmRemote realmRemote;
  //  private DatabaseRemote mRemote;

    public DijkstraAlgorithm(Graph graph, Context context) {
        mNodes = new ArrayList<Point>(graph.getVertexes());
        mEdges = new ArrayList<Edge>(graph.getEdges());
        this.context = context;
//        realmRemote= new RealmRemote(context);
//        mRemote = new DatabaseRemote(context);
//        try {
//            mRemote.openDatabase();
//        } catch (SQLDataException e) {
//            e.printStackTrace();
//        }
    }

    public void execute(Point source) {
        mSettledNodes = new HashSet<Point>();
        mUnsettledNodes = new HashSet<Point>();
        mDistance = new HashMap<Point, Float>();
        mPredecessors = new HashMap<Point, Point>();
        mDistance.put(source, 0F);
        mUnsettledNodes.add(source);
        while (mUnsettledNodes.size() > 0) {
            Point node = getMinimum(mUnsettledNodes);
            mSettledNodes.add(node);
            mUnsettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Point node) {
        List<Point> adjacentNodes = getNeighbors(node);
        Log.i(TAG, "size adjacentNodes=" + adjacentNodes.size());
        for (Point target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                + getDistance(node, target)) {
                mDistance.put(target, getShortestDistance(node)
                    + getDistance(node, target));
                mPredecessors.put(target, node);
                mUnsettledNodes.add(target);
            }
        }
    }

    private float getDistance(Point node, Point target) {
        for (Edge edge : mEdges) {
            if (realmRemote.getObjectPointFromName(edge.getSource()).equals(node)
                && realmRemote.getObjectPointFromName(edge.getDestination()).equals(target)) {
                return edge.getWeigth();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Point> getNeighbors(Point node) {
        List<Point> neighbors = new ArrayList<Point>();
        for (Edge edge : mEdges) {
            if (realmRemote.getObjectPointFromName(edge.getSource()).equals(node)
                && !isSettled(realmRemote.getObjectPointFromName(edge.getDestination()))) {
                neighbors.add(realmRemote.getObjectPointFromName(edge.getDestination()));
            }
        }
        return neighbors;
    }

    private boolean isSettled(Point vertex) {
        return mSettledNodes.contains(vertex);
    }

    private Point getMinimum(Set<Point> vertexes) {
        Point minimum = null;
        for (Point vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private float getShortestDistance(Point destination) {
        Float d = mDistance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
    * This method returns the path from the source to the selected target
    * and NULL if no path exists
    * */
    public LinkedList<Point> getPath(Point target) {
        LinkedList<Point> path = new LinkedList<Point>();
        Point step = target;
        // check if a path exists
        if (mPredecessors.get(step) == null) {
            Log.i(TAG, "mPredecessors.get(step) == null");
            return null;
        }
        path.add(step);
        while (mPredecessors.get(step) != null) {
            step = mPredecessors.get(step);
            Log.i(TAG, "step=" + step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }
}
