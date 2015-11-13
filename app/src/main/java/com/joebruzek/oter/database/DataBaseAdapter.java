package com.joebruzek.oter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DataBaseAdapter provides a clean abstraction from the database and allows easy creation and upgrade of the database
 *
 * Created by jbruzek on 11/13/15.
 */
public class DataBaseAdapter extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "oter.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "";
    private static final String DATABASE_DROP_TABLES = "";

    //Public strings
    public static final String TABLE_OTERS = "oters";
    public static final String COLUMN_ID = "_id";
    //TODO: Add the rest of the column names as public static final strings

    /**
     * Constructor
     * @param context
     */
    public DataBaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create the database by executing the creation SQL
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        //TODO: create the DATABASE_CREATE string
        database.execSQL(DATABASE_CREATE);
    }

    /**
     * Handle the upgrading of the database
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DataBaseAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        //TODO: create the DATABASE_DROP_TABLES string
        database.execSQL(DATABASE_DROP_TABLES);
        onCreate(database);
    }
}
