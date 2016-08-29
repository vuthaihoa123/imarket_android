package com.example.framgia.imarketandroid.data.remote;

import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Point;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by framgia on 24/08/2016.
 */
public class RealmRemote {
    private static Realm mRealm = Realm.getDefaultInstance();
    public static RealmResults<Point> getAllPoint(){
//        RealmResults<Point> results = new RealmResults<Point>();
        return mRealm.where(Point.class).findAll();
    }

    public RealmRemote() {
    }

    public static RealmList<Point> getListPointDisplay(){
        RealmResults<Point> results= null;
        results= mRealm.where(Point.class).equalTo("type", 1).findAll();
        RealmList<Point> mLastResults= new RealmList<>();
        for(Point point:results){
            mLastResults.add(point);
        }
        return mLastResults;
    }

    public static LatLng getLocationFromName(String name){
        Point point= mRealm.where(Point.class).equalTo("name", name).findFirst();
        LatLng latLng= new LatLng(point.getLat(), point.getLng());
        return latLng;
    }

    public static List<Edge> getListEdge(){
        List<Edge> result= new ArrayList<>();
        RealmResults<Edge> results= mRealm.where(Edge.class).findAll();
        return results;
    }
    public static Point getObjectPointFromId(int id){
        Point point= mRealm.where(Point.class).equalTo("id", id).findFirst();
        return point;
    }
    public static Point getObjectPointFromName(String name){
        Point point= mRealm.where(Point.class).equalTo("name", name).findFirst();
        return point;
    }
    public static void savePoint(Point point){
        mRealm.beginTransaction();
        mRealm.copyToRealm(point);
        mRealm.commitTransaction();
    }
    public static void saveEdge(Edge edge){
        mRealm.beginTransaction();
        mRealm.copyToRealm(edge);
        mRealm.commitTransaction();
    }
}
