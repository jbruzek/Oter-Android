package com.joebruzek.oter.notifications;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.joebruzek.oter.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * NotificationManager provides a sourve for creating and managing notifications on the user's device
 *
 *
 * Created by jbruzek on 9/8/16.
 */
public class NotificationHandler {

    private static final AtomicInteger idCount = new AtomicInteger(0);
    private Context context;

    /**
     * Constructor
     */
    public NotificationHandler(Context c) {
        this.context = c;
    }

    /**
     * build and send a notification with just a title and subtitle
     * @param title
     * @param subtitle
     */
    public void sendSimpleNotification(String title, String subtitle) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_launcher);
        builder.setContentTitle(title)
            .setContentText(subtitle)
            .setSmallIcon(R.drawable.ic_launcher)
            .setLargeIcon(icon)
            .setColor(context.getResources().getColor(R.color.primary));
        send(builder);
    }

    /**
     * Send a notification from a builder
     * @param builder
     */
    private void send(NotificationCompat.Builder builder) {
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(NotificationHandler.getNextNotificationId(), builder.build());
    }

    /**
     * Get the next notification Id. Every notification has a unique id.
     * @return
     */
    public static int getNextNotificationId() {
        return idCount.incrementAndGet();
    }
}
