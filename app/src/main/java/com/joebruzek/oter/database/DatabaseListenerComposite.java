package com.joebruzek.oter.database;

import java.util.ArrayList;

/**
 * DatabaseListenerComposite is a class for registering listeners to changes made in the database.
 *
 * Implemented as a singleton and composite, so any listener (that implements DatabaseListener)
 * can register itself using DatabaseComposite.getInstance().registerListener(this);
 *
 * The data layer can make calls to the listener by calling DatabaseListenerComposite.getInstance().onItemUpdated();
 *
 * Created by jbruzek on 11/19/15.
 */
public class DatabaseListenerComposite implements DatabaseListener {

    private ArrayList<DatabaseListener> listeners;
    private static DatabaseListenerComposite instance;

    /**
     * Get the singleton instance of the class
     * @return
     */
    public static DatabaseListenerComposite getInstance() {
        if (instance == null) {
            instance = new DatabaseListenerComposite();
        }
        return instance;
    }

    /**
     * Private constructor
     */
    private DatabaseListenerComposite() {
        listeners = new ArrayList<DatabaseListener>();
    }

    /**
     * Item updated in the database
     */
    @Override
    public void onItemUpdated(String tableName, long id) {
        for (DatabaseListener listener : listeners) {
            listener.onItemUpdated(tableName, id);
        }
    }

    /**
     * Item inserted into the database
     */
    @Override
    public void onItemInserted(String tableName, long id) {
        for (DatabaseListener listener : listeners) {
            listener.onItemInserted(tableName, id);
        }
    }

    /**
     * item deleted from the database
     */
    @Override
    public void onItemDeleted(String tableName, long id) {
        for (DatabaseListener listener : listeners) {
            listener.onItemDeleted(tableName, id);
        }
    }
}
