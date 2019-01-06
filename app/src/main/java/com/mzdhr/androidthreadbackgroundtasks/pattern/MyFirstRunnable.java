package com.mzdhr.androidthreadbackgroundtasks.pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by MohammadL on 06/1/2019
 * Contact me at mmlaif@gmail.com
 *
 * This class used for HandlerThreads Pattern. Along with Main3Activity.java class.
 *
 * Steps:
 * 1. Implement Runnable interface.
 * 2. Override run().
 * 3. Create field for main handler, using weak reference to avoid memory leaks.
 * 4. Do your work on run() then wrap result into the main thread handler, to send it to main thread.
 */
public class MyFirstRunnable implements Runnable{
    // Fields
    private static final String TAG = "MyFirstRunnable";
    private WeakReference<Handler> mMainThreadHandler;

    public MyFirstRunnable(Handler mainThreadHandler) {
        mMainThreadHandler = new WeakReference<>(mainThreadHandler);
    }

    @Override
    public void run() {
        Message message = Message.obtain(null, Constant.ADDITION_TASK);

        int x = 10;
        int y = 10;
        int result = x + y;
        Log.d(TAG, "run: Calculating... 10 + 10 from thread: " + Thread.currentThread().getName());

        Bundle bundle = new Bundle();
        bundle.putInt(Constant.RESULT_KEY, result);
        message.setData(bundle);

        mMainThreadHandler.get().sendMessage(message);  // we used get() because it is weak reference.

    }
}
