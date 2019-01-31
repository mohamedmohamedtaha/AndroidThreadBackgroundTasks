package com.mzdhr.androidthreadbackgroundtasks.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Created by MohammadL on 19/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyFirstReceiver extends BroadcastReceiver {

    private static final String TAG = "MyFirstReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Called! on thread -> " + Thread.currentThread().getName());

        IntentFilter intentFilter = new IntentFilter();
        IntentFilter.
    }
}
