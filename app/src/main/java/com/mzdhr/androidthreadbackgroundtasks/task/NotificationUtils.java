package com.mzdhr.androidthreadbackgroundtasks.task;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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
    private static final String NOTIFICATION_CHANNEL_ID = "The_Awesome_Channel_For_Notifications";

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
        // Create a Notification Manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        // Create Notification Channel on Android 8.0 and Above.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Prepare name, description, and importance (PRIORITY) for our channel. More about: Set the importance level at: https://developer.android.com/training/notify-user/channels#importance
            String name = "Awesome Notification Channel";
            String description = "This channel shows only awesome notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            // Create the Channel (Registering the channel with the system).
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(channel);
            // Set its description
            channel.setDescription(description);
        }


        // Create Notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)

                // Title Text
                .setContentTitle(text)
                // Body Text
                .setContentText(text)
                // If you want more text use Style For longer text | Want more? Check: Create an Expandable Notification at: https://developer.android.com/training/notify-user/expanded
                .setStyle(new NotificationCompat.BigTextStyle().bigText(text))
                // Intent to open when the user tap on the notification (We used our helper method):
                .setContentIntent(contentIntent(context))
                // Close the notification when user tap on it
                .setAutoCancel(true)
                ;


        // More Configuration to the notificationBuilder
        // Notification Intrusive (PRIORITY) on Android 7.1 and Lower.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        // Building the Notification


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
