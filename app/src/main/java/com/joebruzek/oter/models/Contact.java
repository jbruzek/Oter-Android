package com.joebruzek.oter.models;

import android.graphics.Bitmap;

/**
 * Created by jbruzek on 11/20/15.
 */
public class Contact {

    private String name;
    private String number;
    private Bitmap picture;

    /**
     * Empty contstructor. Initializes everything to ""
     */
    public Contact() {
        this("", "");
    }

    /**
     * Contstructor
     * @param name
     * @param number
     */
    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    // Getters and Setters

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }
}
