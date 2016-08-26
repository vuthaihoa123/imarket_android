package com.example.framgia.imarketandroid.data.model;

import android.database.Cursor;

/**
 * Created by nguyenxuantung on 24/06/2016.
 */
public class Point {
    private int id;
    private int type;
    private double lat, lng;
    private String name;
    public Point(Cursor cursor){
        this.id = cursor.getInt(cursor.getColumnIndex("id"));
        this.lat = cursor.getDouble(cursor.getColumnIndex("latitude"));
        this.lng = cursor.getDouble(cursor.getColumnIndex("longtitude"));
        this.type= cursor.getInt(cursor.getColumnIndex("type"));
        this.name= cursor.getString(cursor.getColumnIndex("name"));
    }
    public Point(int id, double lat, double lng, int type, String name  ) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type= type;
        this.name= name;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == -1) ? 0 : id);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        if (id == -1) {
            if (other.id != -1)
                return false;
        } else if (id!=other.id)
            return false;
        return true;
    }
}
