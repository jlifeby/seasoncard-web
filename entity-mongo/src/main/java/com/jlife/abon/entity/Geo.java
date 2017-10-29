package com.jlife.abon.entity;

import java.io.Serializable;

/**
 * @author Khralovich Dzmitry
 */
public class Geo implements Serializable {

    private double lat;
    private double lng;

    public Geo() {
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

    @Override
    public String toString() {
        return getLat() + "," + getLng();
    }

}
