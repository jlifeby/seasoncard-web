package com.jlife.abon.entity;

/**
 * @author Dzmitry Misiuk
 */
public class Address extends Entity {

    private String city;
    private String street;
    private String building;
    private String room;
    private Geo location;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Geo getLocation() {
        return location;
    }

    public void setLocation(Geo location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return city + ", " + street + ", " + building + " " + room;
    }
}
