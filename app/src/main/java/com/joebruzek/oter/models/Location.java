package com.joebruzek.oter.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Oter location.
 *
 * Created by jbruzek on 11/13/15.
 */
public class Location implements Parcelable {

    private String name;
    private double latitude;
    private double longitude;
    private long id;

    /**
     * Needed for Parcelable
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write the Location to a parcel
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        double[] coordinates = new double[]{longitude, latitude};
        parcel.writeDoubleArray(coordinates);
        parcel.writeLong(id);
    }

    /**
     * A creator to build Oters from parcels
     */
    public static final Parcelable.Creator<Location> CREATOR
            = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    /**
     * Default constructor
     */
    public Location() {
        //TODO: initialize. Maybe add alternate constructors that take parameters
    }

    /**
     * Create a location from a parcel
     */
    public Location(Parcel p) {
        name = p.readString();
        double[] coordinates = new double[2];
        p.readDoubleArray(coordinates);
        longitude = coordinates[0];
        latitude = coordinates[1];
        id = p.readLong();
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
