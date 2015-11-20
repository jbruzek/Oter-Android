package com.joebruzek.oter.utilities;

import android.telephony.SmsManager;

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
}
