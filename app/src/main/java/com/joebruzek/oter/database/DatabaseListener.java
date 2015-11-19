package com.joebruzek.oter.database;

/**
 * Interface implemented by classes that want to listen to the database changes
 *
 * Created by jbruzek on 11/19/15.
 */
public interface DatabaseListener {
    void onItemUpdated(String tableName, long id);
    void onItemInserted(String tableName, long id);
    void onItemDeleted(String tableName, long id);
}
