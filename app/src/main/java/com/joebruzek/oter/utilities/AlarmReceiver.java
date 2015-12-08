package com.joebruzek.oter.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.joebruzek.oter.services.SendOterService;

/**
 * Created by jbruzek on 11/26/15.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Recieve a broadcast and start a service to handle it
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", -1);
        SendOterService.startOterService(context, id);
    }
}