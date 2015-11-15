package com.joebruzek.oter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.joebruzek.oter.models.Oter;

import java.util.ArrayList;
import java.util.List;

/**
 * OterDataLayer is an abstraction of the Oter table in the SQLiteDatabase.
 * Provides functions for interacting with the oter data
 *
 * Created by jbruzek on 11/13/15.
 */
public class OterDataLayer {

    private SQLiteDatabase database;
    private DataBaseAdapter adapter;
    private String[] allColumns = {
            //TODO: Add the columns as public static final strings in DataBaseAdapter
            //TODO: Add them to this array
    };

    /**
     * Constructor. Initialize the DatabaseAdapter.
     *
     * @param context
     */
    public OterDataLayer(Context context) {
        adapter = new DataBaseAdapter(context);
    }

    /**
     * open the database for use
     */
    public void openDB() {
        database = adapter.getWritableDatabase();
    }

    /**
     * Close the database when we're done
     */
    public void closeDB() {
        adapter.close();
    }

    /**
     * insert a oter into the database
     *
     * @param oter
     */
    public void insertOter(Oter oter) {
        ContentValues values = new ContentValues();
        //TODO: Add the values from the Oter to the contentValues
        //TODO: i.e. values.put(adapter.COLUMN_TIME, oter.getTime());
        database.insert(adapter.TABLE_OTERS, null, values);
    }

    /**
     * Get a cursor for the database result of querying all oters
     *
     * @param limit the limit for how many oters you want to receive
     * @return
     */
    public Cursor getAllOtersCursor(int limit) {
        String orderBy = adapter.COLUMN_ID + " DESC";
        return database.query(adapter.TABLE_OTERS,
                allColumns, null, null, null, null, orderBy, String.valueOf(limit));
    }

    /**
     * Get a list of all the oters in the database
     *
     * @param limit the limit for how many oters you want to retrieve.
     * @return a list of oters.
     */
    public List<Oter> getAllOters(int limit) {
        List<Oter> oters = new ArrayList<Oter>();

        Cursor cursor = getAllOtersCursor(limit);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            oters.add(getOter(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DATABASE", "getAllOters, size: " + oters.size());
        return oters;
    }

    /**
     * Get a oter object from a cursor.
     *
     * @param cursor
     * @return
     */
    private Oter getOter(Cursor cursor) {
        Oter oter = new Oter();
        //TODO: Get all of the data from the cursor and add it to the oter
        //TODO: e.g. oter.setLocationName(cursor.getString(0));
        return oter;
    }

    /**
     * Get a cursor for the database result of querying all active oters
     *
     * @return a cursor to the query result
     */
    public Cursor getActiveOtersCursor() {
        String whereClause = "active = ?";
        String[] whereArgs = new String[] {"true"};
        String orderBy = adapter.COLUMN_ID + " DESC";
        return database.query(adapter.TABLE_OTERS,
                allColumns, whereClause, whereArgs, null, null, orderBy);
    }


    /**
     * get all of the oters that are "active"
     */
    public List<Oter> getActiveOters() {
        List<Oter> oters = new ArrayList<Oter>();

        Cursor cursor = getActiveOtersCursor();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            oters.add(getOter(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("DATABASE", "getActiveOters, size: " + oters.size());
        return oters;
    }
}