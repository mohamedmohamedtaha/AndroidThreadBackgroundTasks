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
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This Activity used for Thread Pools Pattern. Along with MyThreadPoolRunnable.java class.
 * 1. Create a Handler for main thread.
 * 2. implements Handler.Callback.
 * 3. Override handleMessage().
 **/
public class Main4Activity extends AppCompatActivity implements Handler.Callback {

    private static final String TAG = "Main4Activity";
    private Handler mMainThreadHandler;
    private ArrayList<NameEntity> mNames;
    ExecutorService mExecutorService;
    AppDatabase mAppDatabase;

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == Constant.DATABASE_MULTI_QUERY_TASK) {
            ArrayList<NameEntity> names = Parcels.unwrap(msg.getData().getParcelable(Constant.DATABASE_MULTI_QUERY_RESULT));
            mNames.addAll(names);
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init the list data
        mNames = new ArrayList<>();

        // init the Main Handler
        mMainThreadHandler = new Handler(this);

        // Init Database
        mAppDatabase = AppDatabase.getInstance(getApplication());

        // Init Thread Pool
        // int availableProcessors = Runtime.getRuntime().availableProcessors();
        mExecutorService = Executors.newFixedThreadPool(5);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < 5; i++) {
                    int chunkSize = 2;
                    MyThreadPoolRunnable myThreadPoolRunnable = new MyThreadPoolRunnable(i * chunkSize, chunkSize, mAppDatabase, mMainThreadHandler);
                    mExecutorService.execute(myThreadPoolRunnable); // we use submit not execute for Callable.
                }
//                    mExecutorService.execute(new MyThreadPoolRunnable(0, 2, mAppDatabase, mMainThreadHandler)); // gets 10 and 9
//                    mExecutorService.execute(new MyThreadPoolRunnable(2, 2, mAppDatabase, mMainThreadHandler)); // gets 8 and 7
//                    mExecutorService.execute(new MyThreadPoolRunnable(4, 2, mAppDatabase, mMainThreadHandler)); // gets 6 and 5
//                    mExecutorService.execute(new MyThreadPoolRunnable(6, 2, mAppDatabase, mMainThreadHandler)); // gets 4 and 3
//                    mExecutorService.execute(new MyThreadPoolRunnable(8, 2, mAppDatabase, mMainThreadHandler)); // gets 2 and 1


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStop() {
        // Shutdown the Pool Thread
        mExecutorService.shutdownNow();
        super.onStop();
    }

    public void logResult(View view) {
        // Arrange the ArrayList with a Comparator by Id
        Collections.sort(mNames, new Comparator<NameEntity>() {
            @Override
            public int compare(NameEntity o1, NameEntity o2) {
                return o1.getId() - o2.getId();
            }
        });

        // Loop and Log data in the ArrayList
        for (int i = 0; i < mNames.size(); i++) {
            Log.d(TAG, "handleMessage: " + mNames.get(i).getName() + " - " + mNames.get(i).getNumber() + " - " + mNames.get(i).getId());
        }
    }

}
