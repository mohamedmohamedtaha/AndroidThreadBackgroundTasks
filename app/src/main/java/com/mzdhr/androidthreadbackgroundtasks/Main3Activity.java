package com.mzdhr.androidthreadbackgroundtasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.pattern.MyFirstRunnable;

/**
 * This Activity used for HandlerThreads Pattern. Along with MyFirstRunnable.java class.
 *
 * Steps:
 * 1. Implement Handler.Callback.
 * 2. Override handleMessage().
 * 3. Create an instance field for HandlerThread.
 * 4. Create an instance field for a Handler to this main thread.
 * 5. Override method onStart() and init the HandlerThread and the MainThreadHandler.
 * 6. Override method onStop() and stop the HandlerThread.
 * 7. Create a handler for the background thread, with the help of HandlerThread.
 * 8. Post (send) your custom runnable into it.
 */
public class Main3Activity extends AppCompatActivity implements Handler.Callback {

    // Fields
    private static final String TAG = "Main3Activity";
    private HandlerThread mHandlerThread;
    private Handler mMainThreadHandler;


    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == Constant.MULTIPLICATION_TASK) {
            Log.d(TAG, "handleMessage: Result... " + Thread.currentThread().getName());
        }

        if (msg.what == Constant.ADDITION_TASK) {
            int result = msg.getData().getInt(Constant.RESULT_KEY, -1);
            Log.d(TAG, "handleMessage: Result... " + result + " from thread: " + Thread.currentThread().getName());
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 7 & 8
                Handler backgroundHandler = new Handler(mHandlerThread.getLooper());
                backgroundHandler.post(new MyFirstRunnable(mMainThreadHandler));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Init mHandlerThread
        mHandlerThread = new HandlerThread("My Awesome Thread");
        mHandlerThread.start();

        // Init a Handler for this thread
        mMainThreadHandler = new Handler(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandlerThread.quit();        // Stop it immediately.
        //mHandlerThread.quitSafely();  // Finish processing all messages then stop the looper and quit.
    }
}
