package com.joebruzek.oter.models;


/**
 * Data model for an oter.
 *
 * Created by jbruzek on 11/13/15.
 */
public class Oter {

    private String message;
    private int time;
    private Location location;

    /**
     * Default constructor
     */
    public Oter() {
        //TODO: initialize. Maybe make alternate constructors that take parameters
    }

    /**
     * Getters and setters
     */

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
