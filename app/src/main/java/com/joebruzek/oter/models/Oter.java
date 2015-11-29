package com.joebruzek.oter.models;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Data model for an oter.
 *
 * Created by jbruzek on 11/13/15.
 */
public class Oter implements Parcelable {

    private String message;
    private int time;
    private Location location;
    private long id;
    private ArrayList<String> contacts;

    @Override
    public String toString() {
        String c = "";
        for (String s : contacts) {
            c = c + s + "\n";
        }
        return "OTER:\nid: " + id + "\nmessage: " + message + "\ntime: " + time + "\nlocation: " + location + "\ncontacts: " + c;
    }

    /**
     * Describe the contents of the parcel
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write the values of the Oter to a parcel
     * @param parcel
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(message);
        parcel.writeInt(time);
        parcel.writeParcelable(location, flags);
        parcel.writeLong(id);
        parcel.writeStringList(contacts);
    }

    /**
     * A creator to build Oters from parcels
     */
    public static final Parcelable.Creator<Oter> CREATOR
            = new Parcelable.Creator<Oter>() {
        public Oter createFromParcel(Parcel in) {
            return new Oter(in);
        }

        public Oter[] newArray(int size) {
            return new Oter[size];
        }
    };

    /**
     * Default constructor
     */
    public Oter() {
        //default values
        time = 15;
        message = "";
        location = null;
        contacts = new ArrayList<String>();
    }

    /**
     * Create an oter from a parcel
     */
    public Oter(Parcel p) {
        message = p.readString();
        time = p.readInt();
        location = (Location)p.readParcelable(Location.class.getClassLoader());
        id = p.readLong();
        contacts = p.createStringArrayList();
    }

    /**
     * Getters and setters
     */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<String> contacts) {
        this.contacts = contacts;
    }

}