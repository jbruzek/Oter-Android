package com.joebruzek.oter.utilities;

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
    public static String buildGooglePlacesQuery(String location) {
        //TODO: implement
        return "";
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
}
