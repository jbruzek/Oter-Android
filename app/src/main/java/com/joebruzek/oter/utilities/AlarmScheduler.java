package com.joebruzek.oter.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.services.SendOterService;

import java.util.GregorianCalendar;

/**
 * Created by jbruzek on 11/26/15.
 */
public class AlarmScheduler {

    public static void scheduleWakeUp(Context c, Oter o, int time) {
        Intent intent = new Intent(c, AlarmReceiver.class);
        intent.putExtra("id", o.getId());
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, new GregorianCalendar().getTimeInMillis() + time * 60000,
                PendingIntent.getBroadcast(c, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public static void scheduleWakeUp(Context c) {
        Intent intent = new Intent(c, AlarmReceiver.class);
        AlarmManager alarmManager = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, new GregorianCalendar().getTimeInMillis() + 5000,
                PendingIntent.getBroadcast(c, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

}
