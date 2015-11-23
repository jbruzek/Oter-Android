package com.joebruzek.oter.utilities;

import android.content.Context;

import com.joebruzek.oter.R;

/**
 * Strings provides helper methods for formatting strings
 *
 * Created by jbruzek on 11/13/15.
 */
public class Strings {

    /**
     * Take a location name and build a google places test search query
     * @param location the search query (or location name)
     * @return a formatted query, i.e. https://maps.googleapis.com/maps/api/place/textsearch/json?query=manhattan&key=AIzaSyCCpWXxqSycqLzMGvkEDiGNbIaq0FNOhH8
     */
    public static String buildGooglePlacesQuery(Context c, String location) {
        return "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + buildAPIFormattedString(location) + "&key=" + c.getResources().getString(R.string.google_places_api_key);
        //TODO: implement
//        return "";
    }

    /**
     * Change a string from "Burger King" to "Burger+King"
     * @param s
     * @return
     */
    private static String buildAPIFormattedString(String s) {
        return s.trim().replaceAll(" ", "+");
    }

    /**
     * Build a time remaining string for an Oter
     * @param minutes The time (in minutes) away
     * @return formatted string, i.e. "15 minutes away"
     */
    public static String buildTimeString(int minutes) {
        //TODO: implement better
        return String.valueOf(minutes) + " minutes away";
    }

    /**
     * Build a time remaining string with a location name.
     * @param minutes the time (in minutes) away
     * @param location the location name
     * @return Formatted string, i.e. "15 minutes from Manhattan"
     */
    public static String buildTimeString(int minutes, String location) {
        //TODO: implement
        return String.valueOf(minutes) + " minutes away from " + location;
    }

    /**
     * get the initials from a name, in upper case
     *
     * @param name "Joe Bruzek"
     * @return "JB"
     */
    public static String getInitials(String name) {
        if (name == null || name.equals("")) {
            return "";
        }
        String[] tokens = name.split(" ");
        if (tokens.length == 1) {
            return String.valueOf(tokens[0].charAt(0)).toUpperCase();
        }
        return String.valueOf(tokens[0].charAt(0)).toUpperCase() + String.valueOf(tokens[tokens.length - 1].charAt(0)).toUpperCase();
    }
}
