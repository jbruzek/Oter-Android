package com.joebruzek.oter.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

/**
 * Created by jbruzek on 11/26/15.
 */
public class AlarmReceiver extends BroadcastReceiver implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    public void onReceive(Context context, Intent intent) {
        buildGoogleApiClient(context);
        int[] temp = intent.getIntArrayExtra("ids");
        for (int i=0; i < temp.length; i++) {
            temp[i]+=2;
        }
        Log.e("RECEIVE", "got it " + temp[0] + " " + temp[1] + " " + temp[2]);
        AlarmScheduler.scheduleWakeUp(context, temp);
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