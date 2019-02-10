package com.mzdhr.androidthreadbackgroundtasks.task;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.mzdhr.androidthreadbackgroundtasks.MainActivity;
import com.mzdhr.androidthreadbackgroundtasks.R;

/**
 * Created by MohammadL on 10/2/2019
 * Contact me at mmlaif@gmail.com
 *
 * This class used to make Notifications more reusable in our Apps.
 *
 * For More Information:
 * - Notifications Overview: https://developer.android.com/guide/topics/ui/notifiers/notifications
 * - Create a Notification: https://developer.android.com/training/notify-user/build-notification
 */
public class NotificationUtils {

    // Used for Accessing, Canceling, or Updating the notification.
    private static final int NOTIFICAITON_ID = 1000;

    // Channel
    private static final String NOTIFICAIOTN_CHANNEL = "The_Awesome_Channel_For_Notifications";

    // Button Action
    private static final int ACTION_ONE = 1001;
    private static final int ACTION_TWO = 1002;

    // Pending Intent IDs
    private static final int PENDING_INTENT_ID = 2000;


    /**
     * Used to clear all Notifications in the user screen.
     * @param context
     */
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    /**
     * Used to show the notification to the user.
     * @param context
     */
    public static void showNotification(Context context) {

    }


    /**
     * Helper method used to create a first action for notification button.
     * @param context
     * @return
     */
    private static NotificationCompat.Action ActionOne(Context context) {

    }

    /**
     * Helper method used to create a second action for notification button.
     * @param context
     * @return
     */
    private static NotificationCompat.Action ActionTwo(Context context) {

    }

    /**
     * Helper method used to create a Pending Intent.
     * @param context
     * @return
     */
    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return pendingIntent;
    }

    /**
     * Helper method used to create a large icon from Drawable folder images.
     * @param context
     * @return
     */
    private static Bitmap largeIcon(Context context) {
        Resources resources = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background);
        return largeIcon;
    }

}
