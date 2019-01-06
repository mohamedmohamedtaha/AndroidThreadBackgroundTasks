package com.mzdhr.androidthreadbackgroundtasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.pattern.MyFirstThread;

/**
 * This Activity used for Thread, Looper, Handler Pattern. Along with MyFirstThread.java class.
 *
 * Steps:
 * 1. Implement Handler.Callback.
 * 2. Override handleMessage().
 * 3. Create an instance field for MyFirstThread class.
 * 4. Create an instance field for a Handler.
 * 5. Init the Handler in onCreate().
 * 6. Override method onStart() and init the custom thread instance MyFirstThread.
 * 7. Override method onStop() and quit the custom thread instance MyFirstThread.
 *
 * Sending Task:
 * 8. Send your work to the background custom thread, here I used the fab button,
 * otherwise onResume() after onStart() is good place.
 *
 * Retrieving Result:
 * 9. In handleMessage() retrieve your message from the custom thread and extract the result from it.
 */
public class Main2Activity extends AppCompatActivity implements Handler.Callback{

    // Fields
    private static final String TAG = "Main2Activity";
    private MyFirstThread mMyFirstThread;
    private Handler mMainThreadHandler = null;

    // Tasks
    public static int MULTIPLICATION_TASK = 101;
    public static int ADDITION_TASK = 102;
    public static String RESULT_KEY = "result_key";

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MULTIPLICATION_TASK:
                Log.d(TAG, "handleMessage: Current thread name is " + Thread.currentThread().getName());
                int result = msg.getData().getInt(RESULT_KEY, -1);
                Log.d(TAG, "handleMessage: Result for multiplication task is: " + result);
                break;

            case ADDITION_TASK:
                Log.d(TAG, "handleMessage: Current thread name is " + Thread.currentThread().getName());
                break;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a message to
                Message message = Message.obtain(null, MULTIPLICATION_TASK);
                mMyFirstThread.sendMessageToFirstThread(message);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Init Handler
        mMainThreadHandler = new Handler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMyFirstThread = new MyFirstThread(mMainThreadHandler);
        mMyFirstThread.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMyFirstThread.quit();
    }
}
