package com.mzdhr.androidthreadbackgroundtasks.thread;

import android.util.Log;

/**
 * Created by MohammadL on 31/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyThread extends Thread {

    private static final String TAG = "MyThread";

    @Override
    public void run() {
        super.run();
        Log.d(TAG, "run: I'm running from Thread -> " + Thread.currentThread().getName());
    }

}
