package com.joebruzek.oter.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Oter location.
 *
 * Created by jbruzek on 11/13/15.
 */
public class Location implements Parcelable {

    /**
     * Static method to take a JSONObject from a Google places search and turns it into a location list
     * @param json
     * @return
     */
    public static List<Location> getLocationsFromJSON(JSONObject json) {
        List<Location> locations = new ArrayList<Location>();
        try {
            JSONArray results = json.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                Location l = new Location();
                l.setName(results.getJSONObject(i).getString("name"));
                l.setAddress(results.getJSONObject(i).getString("formatted_address"));
                JSONObject locationJSON = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                l.setLatitude(locationJSON.getDouble("lat"));
                l.setLongitude(locationJSON.getDouble("lng"));
                locations.add(l);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return locations;
    }

    private String name;
    private String address;
    private String nickname;
    private double latitude;
    private double longitude;
    private long id;

    @Override
    public String toString() {
        return "LOCATION:\nid: " + id + "\nname: " + name + "\nnickname: " + nickname + "\naddress: " + address + "\nlon: " + longitude + "\nlat: " + latitude;
    }

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
        parcel.writeString(address);
        parcel.writeString(nickname);
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
        //default values
        latitude = 0.0;
        longitude = 0.0;
        nickname = "";
    }

    /**
     * Create a location from a parcel
     */
    public Location(Parcel p) {
        name = p.readString();
        address = p.readString();
        nickname = p.readString();
        double[] coordinates = new double[2];
        p.readDoubleArray(coordinates);
        longitude = coordinates[0];
        latitude = coordinates[1];
        id = p.readLong();
    }

    /**
     * Get the preferred name for this location. This is either the nickname or the formal name if the nickname doesn't exist.
     * @return
     */
    public String getPreferredName() {
        if (!nickname.equals("")) {
            return nickname;
        }
        return name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
