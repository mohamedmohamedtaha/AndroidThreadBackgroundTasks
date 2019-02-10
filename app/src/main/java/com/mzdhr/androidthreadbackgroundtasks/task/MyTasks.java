package com.mzdhr.androidthreadbackgroundtasks.task;

import android.content.Context;
import android.util.Log;

/**
 * Created by MohammadL on 10/2/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyTasks {

    // Fields
    private static final String TAG = "MyTasks";

    // Our Task Actions
    public static final String ACTION_MULTIPLICATION_PROCESS = "multiplication-process";
    public static final String ACTION_ADDITION_PROCESS = "addition-process";
    public static final String ACTION_SHOW_NOTIFICATION = "show-notification";


    public static void executeTask(Context context, String action) {
        if (ACTION_MULTIPLICATION_PROCESS.equals(action)) {
            doMultiplication();
        } else if (ACTION_ADDITION_PROCESS.equals(action)) {
            doAddition();
        } else if (ACTION_SHOW_NOTIFICATION.equals(action)) {
            showNotification(context);
        }
    }


    private static void doMultiplication() {
        int result = 2 * 2;
        Log.d(TAG, "doMultiplication: " + result);
    }

    private static void doAddition() {
        int result = 2 + 2;
        Log.d(TAG, "doAddition: " + result);
    }

    private static void showNotification(Context context) {
        NotificationUtils.showNotification(context);
    }


}


