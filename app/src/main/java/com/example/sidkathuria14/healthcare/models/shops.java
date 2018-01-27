package com.example.sidkathuria14.healthcare.models;

/**
 * Created by sidkathuria14 on 28/1/18.
 */

public class shops {

    int id;
    double lat,lon;

    String distance,name,address;



    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public shops(int id, double lat, double lon, String distance, String name, String address) {

        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.distance = distance;
        this.name = name;
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
