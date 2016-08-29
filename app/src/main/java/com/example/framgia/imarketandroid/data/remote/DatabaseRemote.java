package com.example.framgia.imarketandroid.data.remote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.framgia.imarketandroid.data.model.Edge;
import com.example.framgia.imarketandroid.data.model.Point;
import com.google.android.gms.maps.model.LatLng;

import java.sql.SQLDataException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class DatabaseRemote {
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public DatabaseRemote(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void openDatabase() throws SQLDataException {
        db = helper.getWritableDatabase();
    }

    public void closeDatabase() throws SQLDataException {
        db.close();
    }

    public long savePoint(Point point) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put("latitude", point.getLat());
        values.put("longtitude", point.getLng());
        values.put("type", point.getType());
        values.put("name", point.getName());
        try {
            result = db.insertOrThrow(DatabaseHelper.TABLE_POINT, null, values);
        } catch (Exception e) {

        }
        return result;
    }

    public long saveEdge(Edge edge) {
        long result = -1;
        ContentValues values = new ContentValues();
        values.put("id_start", edge.getNameStart());
        values.put("id_end", edge.getNameEnd());
        values.put("edge", edge.getDistance());
        try {
            result = db.insertOrThrow(DatabaseHelper.TABLE_EDGE, null, values);
        } catch (Exception e) {

        }
        return result;
    }

    public LatLng getPointFromId(int id) {
        LatLng latLng = null;
        String query = "id = " + id;
        Cursor cursor = db.query(true, DatabaseHelper.TABLE_POINT, null, query, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            latLng = new LatLng(cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longtitude")));
        }
        cursor.close();
        return latLng;
    }

    public LatLng getObjectPointFromName(String name) {
        LatLng latLng = null;
        String query = "select * from " + DatabaseHelper.TABLE_POINT+" where name like '"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            latLng = new LatLng(cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longtitude")));
        }
        return latLng;
    }

    public Point getPointFromName(String name) {
       Point point = null;
        String query = "select * from " + DatabaseHelper.TABLE_POINT+" where name like '"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() >0) {
            cursor.moveToFirst();
            point = new Point(1, cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longtitude")),
                    cursor.getInt(cursor.getColumnIndex("type")), cursor.getString(cursor.getColumnIndex("name")));
            cursor.close();
        }
        return point;
    }
    public Point getObjectPointFromId(int id) {
        Point point = null;
        String query = "id = " + id;
        Cursor cursor = null;
        cursor = db.query(true, DatabaseHelper.TABLE_POINT, null, query, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            point = new Point(id, cursor.getDouble(cursor.getColumnIndex("latitude")),
                    cursor.getDouble(cursor.getColumnIndex("longtitude")),
                    cursor.getInt(cursor.getColumnIndex("type")), cursor.getString(cursor.getColumnIndex("name")));
            cursor.close();
        }
            return point;
    }

    public List<Point> getListPoint() {
//        String query = "select * from " + DatabaseHelper.TABLE_POINT;
//        List<Point> list = new ArrayList<>();
//        Cursor cursor = db.rawQuery(query, null);
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                list.add(new Point(cursor));
//                cursor.moveToNext();
//            }
//        }
//        return list;
        List<Point> list = new ArrayList<>();
        String query = "type = 1";
        Cursor cursor = null;
        cursor = db.query(true, DatabaseHelper.TABLE_POINT, null, query, null, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new Point(cursor));
                cursor.moveToNext();
            }
        }
        return list;
    }

    public List<Point> getAllPoint() {
        String query = "select * from " + DatabaseHelper.TABLE_POINT;
        List<Point> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new Point(cursor));
                cursor.moveToNext();
            }
        }
        return list;
    }

    public List<Edge> getListEdge() {
        String query = "select * from " + DatabaseHelper.TABLE_EDGE;
        List<Edge> list = new ArrayList<>();
        Cursor cursor = null;
        cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(new Edge(cursor));
                cursor.moveToNext();
            }
        }
        return list;
    }

    public void deleteDistance() {
        db.execSQL("drop table if exists " + DatabaseHelper.TABLE_EDGE);
        db.execSQL(DatabaseHelper.CREATE_DISTANCE);
    }

    public void deleteData() {
        db.execSQL("drop table if exists " + DatabaseHelper.TABLE_POINT);
        db.execSQL("drop table if exists " + DatabaseHelper.TABLE_EDGE);
        db.execSQL(DatabaseHelper.CREATE_POINT);
        db.execSQL(DatabaseHelper.CREATE_DISTANCE);
    }
}