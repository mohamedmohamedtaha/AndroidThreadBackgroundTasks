package com.mzdhr.androidthreadbackgroundtasks.thread;

import android.util.Log;

/**
 * Created by MohammadL on 31/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyRunnable implements Runnable {

    private static final String TAG = "MyRunnable";

    @Override
    public void run() {
        Log.d(TAG, "run: I'm running from Thread -> " + Thread.currentThread().getName());
    }

}
