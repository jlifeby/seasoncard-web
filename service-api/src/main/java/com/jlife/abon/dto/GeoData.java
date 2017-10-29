package com.jlife.abon.dto;

import java.io.Serializable;

/**
 * @author Khralovich Dzmitry
 */
public class GeoData implements Serializable {

    private double lat;
    private double lng;

    public GeoData() {
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
