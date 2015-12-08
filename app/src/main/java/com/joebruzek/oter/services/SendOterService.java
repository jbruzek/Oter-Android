package com.joebruzek.oter.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.joebruzek.oter.database.OterDataLayer;
import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.utilities.AlarmScheduler;
import com.joebruzek.oter.utilities.HttpTask;
import com.joebruzek.oter.utilities.OterWaitingTask;
import com.joebruzek.oter.utilities.SmsSender;
import com.joebruzek.oter.utilities.Strings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SendOterService is a service that is created by Oter to exist independent of the app and send any
 * Oters at the appropriate time
 *
 * Created by jbruzek on 11/14/15.
 */
public class SendOterService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, HttpTask.HttpCaller {

    private OterDataLayer oterLayer;
    private long id;
    private Oter oter;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    /**
     * Static method used to start a service for an oter
     * @param c
     * @param id
     */
    public static void startOterService(Context c, long id) {
        Intent intent = new Intent(c, SendOterService.class);
        intent.putExtra("id", id);
        c.startService(intent);
    }

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

        oterLayer = new OterDataLayer(this);
        oterLayer.openDB();
        id = intent.getLongExtra("id", -1);

        if (id == -1) {
            Log.e("SendOterService", "Oter id not received by service");
            this.stopSelf();
        }

        oter = oterLayer.getOter(id);
        if (oter == null) {
            this.stopSelf();
        }

        buildGoogleApiClient(this);

        // Return START_STICKY so that the system will restart
        // the service if it gets killed for some reason
        return Service.START_STICKY;
    }

    /**
     * Called when the service is ending
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Process the results from the HttpTask about the time remaining to send an oter
     * @param result
     */
    @Override
    public void processResults(JSONObject result) {
        int minutes = getTime(result);
        if (minutes <= oter.getTime()) {
            SmsSender.sendText(oter);
            oterLayer.removeOter(oter);
        } else {
            AlarmScheduler.scheduleWakeUp(this, oter, minutes - oter.getTime());
        }
        //our work here is done
        this.stopSelf();
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

    /**
     * Set up the google API client that checks the location of the phone
     * @param c
     */
    protected synchronized void buildGoogleApiClient(Context c) {
        mGoogleApiClient = new GoogleApiClient.Builder(c)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Get the location when the google location api connects
     * query for the time remaining
     * @param bundle
     */
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null && oter != null) {
            //location has been found
            new HttpTask(this).execute(
                    Strings.buildGoogleDirectionsQuery(
                            this, mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                            oter.getLocation().getLatitude(), oter.getLocation().getLongitude()));
        } else if (mLastLocation != null) {
            if (id < 0) {
                this.stopSelf();
                return;
            }
            oter = oterLayer.getOter(id);
            if (oter == null) {
                this.stopSelf();
            } else {
                new HttpTask(this).execute(
                        Strings.buildGoogleDirectionsQuery(
                                this, mLastLocation.getLatitude(), mLastLocation.getLongitude(),
                                oter.getLocation().getLatitude(), oter.getLocation().getLongitude()));
            }
        } else {
            //I don't know what to do
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        //nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //nothing
    }

    /**
     * Get the time in minutes remaining till a destination given a google directions response
     * @param result
     * @return -1 if invalid response
     */
    public int getTime(JSONObject result) {
        try {
            JSONArray routes = result.getJSONArray("routes");
            JSONObject route = routes.getJSONObject(0);
            JSONArray legs = route.getJSONArray("legs");
            JSONObject leg = legs.getJSONObject(0);
            JSONObject duration = leg.getJSONObject("duration");
            int time = duration.getInt("value");
            return time / 60;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
