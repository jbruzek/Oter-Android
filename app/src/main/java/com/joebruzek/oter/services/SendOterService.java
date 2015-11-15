package com.joebruzek.oter.services;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import com.joebruzek.oter.database.OterDataLayer;
import com.joebruzek.oter.utilities.HttpTask;
import com.joebruzek.oter.utilities.OterWaitingTask;
import org.json.JSONObject;

/**
 * SendOterService is a service that is created by Oter to exist independent of the app and send any
 * Oters at the appropriate time
 *
 * Created by jbruzek on 11/14/15.
 */
public class SendOterService extends Service {

    private SQLiteDatabase database;
    private OterDataLayer oterLayer;
    private OterSender sender;

    /**
     * Handle an intent request to the service
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Create the sender if it doesn't exist
        if (sender == null) {
            sender = new OterSender();
        }

        // Return START_STICKY so that the system will restart
        // the service if it gets killed for some reason
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Private class within the OterService that handles the querying process of sending Oters
     */
    private class OterSender implements HttpTask.HttpCaller, OterWaitingTask.OterWaitingTaskListener {

        /**
         * Constructor
         */
        public void OterSender() {

        }

        /**
         * Receive a result from the httpTask. If it
         * @param result
         */
        @Override
        public void processResults(JSONObject result) {

        }

        /**
         * receive a result from the OterWaitingTask
         * @param oters
         */
        @Override
        public void onWaitingTaskCompleted(Integer[] oters) {

        }
    }
}
