package com.mzdhr.androidthreadbackgroundtasks.thread;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.mzdhr.androidthreadbackgroundtasks.R;
import com.mzdhr.androidthreadbackgroundtasks.task.MyJobDispatcher;

import java.util.concurrent.TimeUnit;

public class Main8Activity extends AppCompatActivity {

    private static boolean sIsJobDispatcherInitialized;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Firebase Job Dispatcher

            }
        });
    }


    private void firebaseJobDispatcher(){
        if (sIsJobDispatcherInitialized) {
            return;
        }

        int INTERVAL_MINUTES = 1;
        int INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(INTERVAL_MINUTES)) ;
        int SYNC_FLEXTIME_SECONDS = INTERVAL_SECONDS;

        GooglePlayDriver driver = new GooglePlayDriver(this);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job ourJob = firebaseJobDispatcher.newJobBuilder()
                .setService(MyJobDispatcher.class)
                .setTag("MY_JOB_TAG")
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setConstraints(Constraint.DEVICE_IDLE)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(
                        Trigger.executionWindow(
                                INTERVAL_SECONDS,
                                INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS
                        )
                )
                .setReplaceCurrent(true)
                .build();

        firebaseJobDispatcher.schedule(ourJob);

        sIsJobDispatcherInitialized = true;
    }

    private void cancelFirebaseJobDispatcher(){
        GooglePlayDriver driver = new GooglePlayDriver(this);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        firebaseJobDispatcher.cancel("MY_JOB_TAG");
    }

}
