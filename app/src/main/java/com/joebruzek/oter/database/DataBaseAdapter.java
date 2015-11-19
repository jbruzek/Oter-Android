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
public class DatabaseAdapter extends SQLiteOpenHelper {

    /**
     * Constructor
     * @param context
     */
    public DatabaseAdapter(Context context) {
        super(context, DatabaseContract.DATABASE_NAME, null, DatabaseContract.DATABASE_VERSION);
    }

    /**
     * Create the database by executing the creation SQL
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        for (int i = 0; i < DatabaseContract.DATABASE_CREATE_TABLES_ARRAY.length; i++) {
            database.execSQL(DatabaseContract.DATABASE_CREATE_TABLES_ARRAY[i]);
        }
    }

    /**
     * Handle the upgrading of the database
     * @param database
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(DatabaseAdapter.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");

        for (int i = 0; i < DatabaseContract.DATABASE_DROP_TABLES_ARRAY.length; i++) {
            database.execSQL(DatabaseContract.DATABASE_DROP_TABLES_ARRAY[i]);
        }
        onCreate(database);
    }
}
