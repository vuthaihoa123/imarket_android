package com.example.framgia.imarketandroid.data.remote;

import com.example.framgia.imarketandroid.data.FakeContainer;
import com.example.framgia.imarketandroid.data.model.Category;
import com.example.framgia.imarketandroid.data.model.CustomMarker;
import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Point;
import com.example.framgia.imarketandroid.util.Constants;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by framgia on 24/08/2016.
 */
public class RealmRemote {
    private static Realm mRealm = Realm.getDefaultInstance();

    public static RealmResults<Point> getAllPoint() {
//        RealmResults<Point> results = new RealmResults<Point>();
        return mRealm.where(Point.class).findAll();
    }

    public static <T extends RealmObject> RealmResults getAll(Class<T> tClass) {
        return mRealm.where(tClass).findAll();
    }

    public static <T extends RealmObject> RealmResults getListPointDisplay(Class<T> tClass) {
        return mRealm.where(tClass).equalTo(Constants.FIELD_TYPE, 1).findAll();
    }

    public static RealmList<Point> getListPointDisplay(int type) {
        RealmResults<Point> results = null;
        if (type == 0)
            results = mRealm.where(Point.class).findAll();
        else
            results = mRealm.where(Point.class).equalTo(Constants.FIELD_TYPE, type).findAll();
        RealmList<Point> mLastResults = new RealmList<>();
        for (Point point : results) {
            mLastResults.add(point);
        }
        return mLastResults;
    }

    public static RealmResults<Edge> getListEdgeDisplay() {
        RealmResults<Edge> results = null;
        results = mRealm.where(Edge.class).findAll();
        return results;
    }

    public static LatLng getLocationFromName(String name) {
        Point point = mRealm.where(Point.class).equalTo(Constants.FIELD_NAME, name).findFirst();
        LatLng latLng = new LatLng(point.getLat(), point.getLng());
        return latLng;
    }

    public static List<Edge> getListEdge() {
        RealmResults<Edge> results = mRealm.where(Edge.class).findAll();
        return results;
    }

    public static Point getObjectPointFromId(int id) {
        Point point = mRealm.where(Point.class).equalTo(Constants.FIELD_ID, id).findFirst();
        return point;
    }

    public static Point getObjectPointFromName(String name) {
        Point point = mRealm.where(Point.class).equalTo(Constants.FIELD_NAME, name).findFirst();
        return point;
    }

    public static void deletePoint(final String name) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Point> results =
                    mRealm.where(Point.class).equalTo(Constants.FIELD_NAME, name).findAll();
                results.deleteAllFromRealm();
            }
        });
    }

    public static void savePoint(Point point) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(point);
        mRealm.commitTransaction();
    }

    public static void saveObject(Object object) {
        mRealm.beginTransaction();
        mRealm.copyToRealm((RealmObject) object);
        mRealm.commitTransaction();
    }

    public static void saveEdge(Edge edge) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(edge);
        mRealm.commitTransaction();
    }

    public static CustomMarker createCustomMarkerFromPoint(Point point) {
        Category category1 =
            new Category(Integer.toString(point.getType()), Constants.DEMO_CATEGORY);
        CustomMarker result = new CustomMarker(point.getId(), point.getLat(), point.getLng(),
            Constants.DEMO_NUMBER,
            category1);
        return result;
    }
}
