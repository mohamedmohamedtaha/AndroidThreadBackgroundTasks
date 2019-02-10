package com.mzdhr.androidthreadbackgroundtasks.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by MohammadL on 10/2/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyLocalReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get data from the intent that runs this broadcast.
        int number = intent.getIntExtra("number", 0);

        // Create an intent and put result in it, to send it back.
        Intent returningIntent = new Intent();
        intent.putExtra("number", number * 2);

        // Send intent and result back by using Local Broadcast.
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.sendBroadcast(returningIntent);
    }
}
