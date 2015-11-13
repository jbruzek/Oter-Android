package com.joebruzek.oter.models;

/**
 * Oter location.
 *
 * Created by jbruzek on 11/13/15.
 */
public class Location {

    private String name;
    private double latitude;
    private double longitude;

    /**
     * Default constructor
     */
    public Location() {
        //TODO: initialize. Maybe add alternate constructors that take parameters
    }

    /**
     * Getters and setters
     */

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
