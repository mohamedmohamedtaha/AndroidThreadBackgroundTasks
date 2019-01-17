package com.mzdhr.androidthreadbackgroundtasks.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
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

        // Send to Local Broadcast
        Intent intentForBroadcast = new Intent("action.my_started_service.to.activity");
        intentForBroadcast.putExtra(Constant.RESULT_NUMBER, String.valueOf(resultNumber));
        sendBroadcast(intentForBroadcast);


        // Background Thread (If you want to make long task, or use IntentService)
        AsyncTask task = new BackgroundAsyncTask().execute();

        // Return Tags
        // If Android destroy our service, it gonna restart. Intent won't lost with this tag.
        return Service.START_REDELIVER_INTENT;

        // Restart the service, but Intent lost.
        //return Service.START_STICKY;

        // No Restart, Intent lost.
        //return START_NOT_STICKY;
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


    private class BackgroundAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            // Do our long work on the background and different thread.
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // We can stop our service here like:
            // stopSelf();
        }

    }

}
