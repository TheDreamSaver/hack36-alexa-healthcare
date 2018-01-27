package com.example.sidkathuria14.healthcare.models;

/**
 * Created by sidkathuria14 on 27/1/18.
 */

public class stores {
    private String name,alternative_medicine,address;
    private double lat,lng;

    public stores(String name, String alternative_medicine, String address, double lat, double lng) {
        this.name = name;
        this.alternative_medicine = alternative_medicine;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }
public stores(){

}

    public String getName() {
        return name;
    }

    public String getAlternative_medicine() {
        return alternative_medicine;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
