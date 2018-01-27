package com.example.sidkathuria14.healthcare.models;

import java.util.ArrayList;

/**
 * Created by sidkathuria14 on 27/1/18.
 */

public class stores {
    private String name,medicine, unit;
    private ArrayList<shops> shops;

    private cordinates coordinates;

    ArrayList<String> alternate;

    public stores(String name, String medicine, String unit,
                  ArrayList<com.example.sidkathuria14.healthcare.models.shops> shops,
                  cordinates coordinates, ArrayList<String> alternate) {
        this.name = name;
        this.medicine = medicine;
        this.unit = unit;
        this.shops = shops;
        this.coordinates = coordinates;
        this.alternate = alternate;
    }

    public String getName() {
        return name;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getUnit() {
        return unit;
    }

    public ArrayList<com.example.sidkathuria14.healthcare.models.shops> getShops() {
        return shops;
    }

    public cordinates getCoordinates() {
        return coordinates;
    }

    public ArrayList<String> getAlternate() {
        return alternate;
    }
}

