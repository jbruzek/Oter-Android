package com.joebruzek.oter.models;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Data model for an oter.
 *
 * Created by jbruzek on 11/13/15.
 */
public class Oter implements Parcelable {

    private String message;
    private int time;
    private Location location;

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
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message);
        parcel.writeInt(time);
        //TODO: Make Location parcelable and put it here
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
        //TODO: initialize. Maybe make alternate constructors that take parameters
    }

    /**
     * Create an oter from a parcel
     */
    public Oter(Parcel p) {
        message = p.readString();
        time = p.readInt();
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
