package com.mzdhr.androidthreadbackgroundtasks.pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.Main2Activity;

/**
 * Created by MohammadL on 06/1/2019
 * Contact me at mmlaif@gmail.com
 * <p>
 * This class used for Thread, Looper, Handler Pattern. Along with Main2Activity.java class.
 * <p>
 * Steps:
 * 0. Extend the class Thread.
 * 1. Create a handler field for Main Thread.
 * 2. Init it in the constructor.
 * <p>
 * 3. Create a custom handler inner class for this thread.
 * 4. Override handleMessage in the custom handler class, and put your work there.
 * <p>
 * 5. Create a field for the custom handler.
 * <p>
 * Running the Looper
 * 6. Override run() to prepare the Looper for this thread.
 * <p>
 * 7. Create a public method e.g. sendMessageToFirstThread() to retrieve messages into this thread.
 * <p>
 * 8. Create a public method e.g. quit() to stop this thread from other components, so no memory leaking.
 */
public class MyFirstThread extends Thread {

    // Fields
    private static final String TAG = "MyFirstThread";
    private Handler mMainThreadHandler = null;  // potential to leak memory.
    private boolean isRunning = false;          // to determine if running or not, helps us to avoid memory leaks.
    private CustomHandler mCustomHandler;

    public MyFirstThread(Handler mainThreadHandler) {
        mMainThreadHandler = mainThreadHandler;
        isRunning = true;   // it is started
    }

    @Override
    public void run() {
        if (isRunning) {
            Looper.prepare();   // prepare the looper, Looper is the engine that loop our tasks in this thread.
            mCustomHandler = new CustomHandler(Looper.myLooper());  // myLooper gonna return the looper for this thread. there is getMainLooper() that return the main thread looper but we don't need it here.
            Looper.loop();      // start looping our loop
        }
    }

    /**
     * Public method to be called from Main2Activity to pass our message to this custom thread.
     *
     * @param message
     */
    public void sendMessageToFirstThread(Message message) {
        // Sometime threads has delay to start immediately,
        // therefore we should try to catch that null error.
        while (true) {
            try {
                mCustomHandler.sendMessage(message);
                break;
            } catch (NullPointerException e) {
                Log.d(TAG, "sendMessageToFirstThread: " + e.getMessage());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * Public method to be called from Main2Activity to stop this message and clear it,
     * so no memory leaks.
     */
    public void quit() {
        isRunning = false;
        mMainThreadHandler = null;
    }

    /**
     * Custom handler class for this thread.
     * A handler takes the messages, receives the messages, carries the messages.
     */
    private class CustomHandler extends Handler {
        public CustomHandler(Looper looper) {
            super(looper);
        }

        /**
         * We put our heavy code that we want to runs on background here. Like room insert, update, query, delete.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Main2Activity.MULTIPLICATION_TASK) {
                Log.d(TAG, "handleMessage: Current thread name is " + Thread.currentThread().getName());

                // Task
                int x = 10;
                int y = 90;
                int result = y * x;
                // Prepare result onto a bundle to send it back to main thread
                Message message = Message.obtain(null, Main2Activity.MULTIPLICATION_TASK);
                Bundle bundle = new Bundle();
                bundle.putInt(Main2Activity.RESULT_KEY, result);
                message.setData(bundle);
                mMainThreadHandler.sendMessage(message);
            }

            if (msg.what == Main2Activity.ADDITION_TASK) {

            }
        }
    }
}
