package com.mzdhr.androidthreadbackgroundtasks.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.Constant;

/**
 * Created by MohammadL on 16/1/2019
 * Contact me at mmlaif@gmail.com
 *
 * Started Service with Main6Activity.java.
 * https://developer.android.com/guide/components/services#java
 */
public class MyStartedService extends Service {

    private static final String TAG = "MyStartedService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: Called.");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Called.");

        int firstNumber = intent.getIntExtra(Constant.FIRST_NUMBER, -1);
        int secondNumber = intent.getIntExtra(Constant.SECOND_NUMBER, -2);

        int resultNumber = firstNumber + secondNumber;
        Log.d(TAG, "onStartCommand: Result -> " + resultNumber);

        // If Android destroy our service, it gonna restart. Intent won't lost with this tag.
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Called.");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Called.");
    }


}
