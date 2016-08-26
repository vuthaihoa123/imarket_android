package com.example.framgia.imarketandroid.data.DataObject;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by framgia on 23/08/2016.
 */
public class Point extends RealmObject{

    private int id;
    private int type;
    private double lat, lng;
    @PrimaryKey
    private String name;
    public Point(){}
    public Point(int id, double lat, double lng, int type, String name  ) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.type= type;
        this.name= name;
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
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
