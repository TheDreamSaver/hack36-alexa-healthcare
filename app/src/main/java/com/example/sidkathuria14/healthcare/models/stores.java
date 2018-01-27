package com.example.sidkathuria14.healthcare.models;

import java.util.ArrayList;

/**
 * Created by sidkathuria14 on 27/1/18.
 */

public class stores {
    private String name,alternative_medicine,address,unit;
    private double distance;

    private cordinates coordinates;

   ArrayList<String> alternate;

    public String getUnit() {
        return unit;
    }

    public double getDistance() {
        return distance;
    }

    public cordinates getCoordinates() {
        return coordinates;
    }

    public ArrayList<String> getAlternate() {
        return alternate;
    }

    public stores(String name, String alternative_medicine, String address,
                  String unit, double distance, cordinates coordinates, ArrayList<String> alternate) {
        this.name = name;
        this.alternative_medicine = alternative_medicine;
        this.address = address;
        this.unit = unit;
        this.distance = distance;
        this.coordinates = coordinates;
        this.alternate = alternate;
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


}
