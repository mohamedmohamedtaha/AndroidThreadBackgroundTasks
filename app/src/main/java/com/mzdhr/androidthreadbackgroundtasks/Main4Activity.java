package com.mzdhr.androidthreadbackgroundtasks;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.database.AppDatabase;
import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;
import com.mzdhr.androidthreadbackgroundtasks.pattern.MyThreadPoolRunnable;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This Activity used for Thread Pools Pattern. Along with MyThreadPoolRunnable.java class.
 * 1. Create a Handler for main thread.
 * 2. implements Handler.Callback.
 * 3. Override handleMessage().
 *
 **/
public class Main4Activity extends AppCompatActivity implements Handler.Callback{

    private static final String TAG = "Main4Activity";
    private Handler mMainThreadhandler;
    private ArrayList<NameEntity> mNames;
    ExecutorService mExecutorService;
    AppDatabase mAppDatabase;

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == Constant.DATABASE_MULTI_QUERY_TASK) {
            ArrayList<NameEntity> names = Parcels.unwrap(msg.getData().getParcelable(Constant.DATABASE_MULTI_QUERY_RESULT));
            mNames.addAll(names);

            Log.d(TAG, "handleMessage: names size -> " + names.size());
            Log.d(TAG, "handleMessage: mNames size -> " + mNames.size());
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init Database
        mAppDatabase = AppDatabase.getInstance(getApplication());

        // Init Thread Pool
        // int availableProcessors = Runtime.getRuntime().availableProcessors();
        mExecutorService = Executors.newFixedThreadPool(5);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Thread Pool
                for (int i = 1; i < 4; i++) {
                    MyThreadPoolRunnable myThreadPoolRunnable = new MyThreadPoolRunnable(i, i * 10, mAppDatabase, mMainThreadhandler);
                    mExecutorService.submit(myThreadPoolRunnable);
                }

                // Shutdown the Pool Thread
                mExecutorService.shutdownNow();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
