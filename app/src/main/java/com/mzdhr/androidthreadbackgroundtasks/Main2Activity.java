package com.mzdhr.androidthreadbackgroundtasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.pattern.MyFirstThread;

/**
 * This Activity used for Thread, Looper, Handler Pattern. Along with MyFirstThread.java class.
 * <p>
 * Steps:
 * 1. Implement Handler.Callback.
 * 2. Override handleMessage().
 * 3. Create an instance field for MyFirstThread class.
 * 4. Create an instance field for a Handler.
 * 5. Init the Handler in onCreate().
 * 6. Override method onStart() and init the custom thread instance MyFirstThread.
 * 7. Override method onStop() and quit the custom thread instance MyFirstThread.
 * <p>
 * Sending Task:
 * 8. Send your work to the background custom thread, here I used the fab button,
 * otherwise onResume() after onStart() is good place.
 * <p>
 * Retrieving Result:
 * 9. In handleMessage() retrieve your message from the custom thread and extract the result from it.
 */
public class Main2Activity extends AppCompatActivity implements Handler.Callback {

    // Fields
    private static final String TAG = "Main2Activity";
    private MyFirstThread mMyFirstThread;
    private Handler mMainThreadHandler = null;



    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == Constant.MULTIPLICATION_TASK) {
            int result = msg.getData().getInt(Constant.RESULT_KEY, -1);
            Log.d(TAG, "handleMessage: Result... " + result + " from thread: " + Thread.currentThread().getName());
        }

        if (msg.what == Constant.ADDITION_TASK) {
            Log.d(TAG, "handleMessage: Result... " + Thread.currentThread().getName());
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
                // Create a message and send it to the background custom thread
                Message message = Message.obtain(null, Constant.MULTIPLICATION_TASK);
                mMyFirstThread.sendMessageToFirstThread(message);

                // Just for Fun!
                // Create runnable and send it to the background custom thread
                // Runnable can not return result back by it self
                // So result gonna be in the custom thread
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        // Task
                        int x = 10;
                        int y = 90;
                        Log.d(TAG, "run: a Runnable Calculating... 90 - 10 from thread: " + Thread.currentThread().getName());

                        int result = y - x;
                        Log.d(TAG, "run: Result ... 90 - 10 " + result + " from thread: " + Thread.currentThread().getName());
                    }
                };
                mMyFirstThread.sendRunnableToFirstThread(runnable);
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
