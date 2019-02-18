package com.mzdhr.androidthreadbackgroundtasks.alarms;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by MohammadL on 17/2/2019
 * Contact me at mmlaif@gmail.com
 */
public class IntentServiceForAlarm extends IntentService {

    private static final String TAG = "IntentServiceForAlarm";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentServiceForAlarm(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: " + intent.getStringExtra("NAME_KEY"));
    }
}
