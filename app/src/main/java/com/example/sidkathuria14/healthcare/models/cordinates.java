package com.example.sidkathuria14.healthcare.models;

/**
 * Created by sidkathuria14 on 28/1/18.
 */

public class cordinates {
    private double latitude,longitude;

    public cordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
