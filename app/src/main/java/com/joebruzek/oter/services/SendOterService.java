package com.joebruzek.oter.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.joebruzek.oter.database.OterDataLayer;
import com.joebruzek.oter.utilities.AlarmScheduler;
import com.joebruzek.oter.utilities.HttpTask;
import com.joebruzek.oter.utilities.OterWaitingTask;
import org.json.JSONObject;

/**
 * SendOterService is a service that is created by Oter to exist independent of the app and send any
 * Oters at the appropriate time
 *
 * Created by jbruzek on 11/14/15.
 */
public class SendOterService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private SQLiteDatabase database;
    private OterDataLayer oterLayer;
    private OterSender sender;
    private GoogleApiClient mGoogleApiClient;

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

        buildGoogleApiClient(this);

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
        public OterSender() {
        }

        /**
         * Receive a result from the httpTask. If it
         * @param result
         */
        @Override
        public void processResults(JSONObject result) {
            //TODO: check to see if the location is close enough to send
            //TODO: if it is, send the text to the oter(s)
            //TODO: else send a waitingTask
        }

        /**
         * receive a result from the OterWaitingTask
         * @param oters
         */
        @Override
        public void onWaitingTaskCompleted(Integer[] oters) {
        }
    }

    /**
     * Check the amount of time that it will take to get to the location of this oterid
     *
     * https://maps.googleapis.com/maps/api/directions/json?origin=0.0,0.0&destination=0.0,0.0&mode=driving&key=AIzaSyCCpWXxqSycqLzMGvkEDiGNbIaq0FNOhH8
     *
     * @param oterid
     * @return
     */
    private int checkTime(int oterid) {
        return 0;
    }

    protected synchronized void buildGoogleApiClient(Context c) {
        mGoogleApiClient = new GoogleApiClient.Builder(c)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
