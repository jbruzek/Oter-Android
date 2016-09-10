package com.joebruzek.oter.utilities;

import android.content.Context;
import android.telephony.SmsManager;

import com.joebruzek.oter.models.Oter;
import com.joebruzek.oter.notifications.NotificationHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * SmsSender is a helper class to send sms messages
 *
 * Created by jbruzek on 11/20/15.
 */
public class SmsSender {

    /**
     * Send a text to a number of people.
     * @param text
     * @param contacts
     */
    public static void sendText(String text, List<String> contacts) {
        SmsManager sms = SmsManager.getDefault();
        ArrayList<String> segmentedMessage = sms.divideMessage(text);
        for (String number : contacts) {
            sms.sendMultipartTextMessage(number, null, segmentedMessage, null, null);
        }
    }

    /**
     * Send an oter as a text message
     * @param o
     */
    public static void sendText(Oter o, Context c) {
        sendText(o.getMessage(), o.getContacts());
        new NotificationHandler(c).sendSimpleNotification("Sent Oter", "\"" + o.getMessage() + "\"");
    }
}
