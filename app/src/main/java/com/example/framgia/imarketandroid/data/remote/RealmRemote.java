package com.example.framgia.imarketandroid.data.remote;

import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Point;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by framgia on 24/08/2016.
 */
public class RealmRemote {
    private static Realm mRealm = Realm.getDefaultInstance();

    public static <T extends RealmObject> RealmResults getAll(Class<T> tClass) {
        return mRealm.where(tClass).findAll();
    }

    public static <T extends RealmObject> RealmResults getListPointDisplay(Class<T> tClass) {
        return mRealm.where(tClass).equalTo("type", 1).findAll();
    }

    public static LatLng getLocationFromName(String name) {
        Point point = mRealm.where(Point.class).equalTo("name", name).findFirst();
        LatLng latLng = new LatLng(point.getLat(), point.getLng());
        return latLng;
    }

    public static List<Edge> getListEdge() {
        List<Edge> result = new ArrayList<>();
        RealmResults<Edge> results = mRealm.where(Edge.class).findAll();
        return results;
    }

    public static Point getObjectPointFromId(int id) {
        Point point = mRealm.where(Point.class).equalTo("id", id).findFirst();
        return point;
    }

    public static Point getObjectPointFromName(String name) {
        Point point = mRealm.where(Point.class).equalTo("name", name).findFirst();
        return point;
    }

    public static void saveObject(Object object) {
        mRealm.beginTransaction();
        mRealm.copyToRealm((RealmObject) object);
        mRealm.commitTransaction();
    }
}
