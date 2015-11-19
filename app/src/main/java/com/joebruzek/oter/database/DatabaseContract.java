package com.joebruzek.oter.database;

import android.provider.BaseColumns;

import com.joebruzek.oter.models.Location;

/**
 * Contracts for the database. Includes abstract classes for the tables used in the database
 * and contracts for each of them.
 *
 * Created by jbruzek on 11/18/15.
 */
public final class DatabaseContract {

    public static final  int    DATABASE_VERSION   = 1;
    public static final  String DATABASE_NAME      = "oter.db";
    private static final String TEXT_TYPE          = " TEXT";
    private static final String INTEGER_TYPE       = " INTEGER";
    private static final String REAL_TYPE          = " REAL";
    private static final String COMMA_SEP          = ",";

    public static final String[] DATABASE_CREATE_TABLES_ARRAY = {
            OtersContract.CREATE_TABLE,
            LocationsContract.CREATE_TABLE,
            ContactRelationContract.CREATE_TABLE
    };

    public static final String[] DATABASE_DROP_TABLES_ARRAY = {
            OtersContract.DELETE_TABLE,
            LocationsContract.DELETE_TABLE,
            ContactRelationContract.DELETE_TABLE
    };

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}

    /**
     * Oters table. This stores the oters.
     * Each oter has a foreign key to one location
     */
    public static abstract class OtersContract implements BaseColumns {
        //Don't allow this class to be instantiated
        private OtersContract() {}

        public static final String TABLE_NAME = "Oters";
        public static final String KEY_ID = "Id";
        public static final String KEY_ACTIVE = "Active";
        public static final String KEY_MESSAGE = "Message";
        public static final String KEY_TIME = "Time";
        public static final String KEY_LOCATION = "Location";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_ACTIVE + INTEGER_TYPE + COMMA_SEP +
                KEY_MESSAGE + TEXT_TYPE + COMMA_SEP +
                KEY_TIME + INTEGER_TYPE + COMMA_SEP +
                KEY_LOCATION + INTEGER_TYPE + COMMA_SEP +
                "FOREIGNKEY (" + KEY_LOCATION + ") REFERENCES " +
                LocationsContract.TABLE_NAME + "(" + LocationsContract.KEY_ID + ")" +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String[] ALL_COLUMNS = {
                KEY_ID,
                KEY_ACTIVE,
                KEY_MESSAGE,
                KEY_TIME,
                KEY_LOCATION
        };
    }

    /**
     * Location table.
     */
    public static abstract class LocationsContract implements BaseColumns {
        //Don't allow this class to be instantiated
        private LocationsContract() {}

        public static final String TABLE_NAME = "Locations";
        public static final String KEY_ID = "Id";
        public static final String KEY_NAME = "Name";
        public static final String KEY_LATITUDE = "Latitude";
        public static final String KEY_LONGITUDE = "Longitude";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + TEXT_TYPE + COMMA_SEP +
                KEY_LATITUDE + REAL_TYPE + COMMA_SEP +
                KEY_LONGITUDE + REAL_TYPE + COMMA_SEP +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Contact relation table.
     *
     * A contact relation defines a many to many relationship between contacts and oters
     *
     * For now there is only a foreign key to oters because contacts are not being stored in our database.
     * It is my understanding that we can get either an ID or a URI to store as the foreign key to the contact
     * and look that up in the Android system.
     */
    public static abstract class ContactRelationContract implements BaseColumns {
        //Don't allow this class to be instantiated
        private ContactRelationContract() {}

        public static final String TABLE_NAME = "ContactRelation";
        public static final String KEY_ID = "Id";
        public static final String KEY_OTER = "OterID";
        public static final String KEY_CONTACT = "ContactId";

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_OTER + INTEGER_TYPE + COMMA_SEP +
                KEY_CONTACT + TEXT_TYPE + COMMA_SEP +
                "FOREIGNKEY (" + KEY_OTER + ") REFERENCES " +
                OtersContract.TABLE_NAME + "(" + OtersContract.KEY_ID + ")" +
                " )";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Contact table.
     *
     * Commented out for now. I want to be able to grab a reference to a contact in the phone instead
     * or restoring the information in this database. In that way I can use the reference to get all of the information
     * about the contact, like name, number, picture, etc...
     */
//    public static abstract class ContactsContract implements BaseColumns {
//        //Don't allow this class to be instantiated
//        private ContactsContract() {}
//
//        public static final String TABLE_NAME = "Contacts";
//        public static final String KEY_ID = "Id";
//        public static final String KEY_NAME = "Name";
//        public static final String KEY_NUMBER = "Number";
//        public static final String KEY_OTER = "OterId";
//
//        public static final String CREATE_TABLE = "CREATE TABLE " +
//                TABLE_NAME + " (" +
//                KEY_ID + " INTEGER PRIMARY KEY," +
//                KEY_NAME + TEXT_TYPE + COMMA_SEP +
//                KEY_NUMBER + TEXT_TYPE + COMMA_SEP +
//                KEY_OTER + INTEGER_TYPE + COMMA_SEP +
//                "FOREIGNKEY (" + KEY_OTER + ") REFERENCES " +
//                OtersContract.TABLE_NAME + "(" + OtersContract.KEY_ID + ")" +
//                " )";
//
//        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
//    }
}