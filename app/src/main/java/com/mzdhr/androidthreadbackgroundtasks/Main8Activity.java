package com.mzdhr.androidthreadbackgroundtasks;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.mzdhr.androidthreadbackgroundtasks.task.MyJobDispatcher;
import com.mzdhr.androidthreadbackgroundtasks.task.MyJobManager;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class Main8Activity extends AppCompatActivity {

    private static final String TAG = "Main8Activity";
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


    private void firebaseJobDispatcher() {
        if (sIsJobDispatcherInitialized) {
            return;
        }

        // Time windows, should be a fields.
        int INTERVAL_MINUTES = 1;
        int INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(INTERVAL_MINUTES));
        int SYNC_FLEXTIME_SECONDS = INTERVAL_SECONDS;

        GooglePlayDriver driver = new GooglePlayDriver(this);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);

        Job ourJob = firebaseJobDispatcher.newJobBuilder()
                .setService(MyJobDispatcher.class)
                .setTag("MY_JOB_TAG") // the tag of this job. should be a field.
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setConstraints(Constraint.DEVICE_IDLE)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)  // forever run this job.
                .setRecurring(true) // repeating job.
                .setTrigger( // run between:
                        Trigger.executionWindow(
                                INTERVAL_SECONDS, // 60 seconds
                                INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS // and 120 seconds.
                        )
                )
                .setReplaceCurrent(true) // if job exists with same tag, replace the old one.
                .build();

        firebaseJobDispatcher.schedule(ourJob); // schedule the job with the dispatcher.

        sIsJobDispatcherInitialized = true; // the job has been initialized.
    }

    private void cancelFirebaseJobDispatcher() {
        GooglePlayDriver driver = new GooglePlayDriver(this);
        FirebaseJobDispatcher firebaseJobDispatcher = new FirebaseJobDispatcher(driver);
        firebaseJobDispatcher.cancel("MY_JOB_TAG");
    }


    private void workManager() {


        // Passing data to our works
        Data myData = new Data.Builder()
                .putString("NAME_KEY", "Mohammad")
                .putInt("AGE_KEY", 14)
                .build();


        // This gonna run your work right away.
        // OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyJobManager.class).build();

        // Constraints for our work
        Constraints constraints = new Constraints.Builder()
                .setRequiresDeviceIdle(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();


        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(MyJobManager.class)
                .setInputData(myData)
                .setConstraints(constraints)
                .addTag("MY_WORK_MANAGER_TAG_ONE_TIME")
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyJobManager.class, 12, TimeUnit.HOURS)
                .setInputData(myData)
                .setConstraints(constraints)
                .addTag("MY_WORK_MANAGER_PERIODIC_TAG")
                .build();

        // Send our work to be scheduled.
        WorkManager.getInstance().enqueue(oneTimeWorkRequest);
        WorkManager.getInstance().enqueue(periodicWorkRequest);


        // Canceling our jobs by their Id
        // Get Id for our jobs (after we enqueue them)
        UUID oneTimeWorkRequestId = oneTimeWorkRequest.getId();
        WorkManager.getInstance().cancelWorkById(oneTimeWorkRequestId);

        UUID periodicWorkRequestId = periodicWorkRequest.getId();
        WorkManager.getInstance().cancelWorkById(periodicWorkRequestId);




    }

    private void getResultWorkManager() {
        WorkManager.getInstance().getWorkInfosByTagLiveData("MY_WORK_MANAGER_TAG_ONE_TIME")
                .observe(this, new Observer<List<WorkInfo>>() {
                    @Override
                    public void onChanged(@Nullable List<WorkInfo> workInfos) {
                        if (workInfos != null && workInfos.get(0).getState().isFinished()) {
                            String result = workInfos.get(0).getOutputData().getString("RESULT");
                            Log.d(TAG, "onChanged: " + result);
                        }
                    }
                });
    }

    private void cancelWorkMananger() {
        WorkManager.getInstance().cancelAllWork();
        WorkManager.getInstance().cancelAllWorkByTag("MY_WORK_MANAGER_TAG_ONE_TIME");
    }

    private void chaningWorkManager() {
        // If any task returns Result.failure(), the whole sequence ends.
        WorkManager.getInstance()
                .beginWith(downLoadImageWorkRequest)
                .then(saveDownloadedImageToDatabaseWorkRequest)
                .then(cleanTempWorkRequest)
                .enqueue();

        WorkManager.getInstance()
                .beginWith(Arrays.asList(downloadImagesWorkRequest, downloadTextsWorkRequest, downloadLinksWorkRequest))    // Runs in Parallel.
                .then(cleanTempWorkRequest)
                .enqueue();
    }



}
