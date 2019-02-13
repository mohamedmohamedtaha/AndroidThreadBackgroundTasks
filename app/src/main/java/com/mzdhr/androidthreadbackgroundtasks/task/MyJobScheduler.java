package com.mzdhr.androidthreadbackgroundtasks.task;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

/**
 * Created by MohammadL on 12/2/2019
 * Contact me at mmlaif@gmail.com
 *
 * To run this class from an Activity:
 *     private void scheduleJob() {
 *         PersistableBundle extras = new PersistableBundle();
 *         extras.putString(MyJS.EXTRA_DATA, "This is any type of data you want to pass it into the job to use it when job is triggered");
 *
 *
 *         long ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L;
 *
 *         // Building the Information about our job
 *         ComponentName componentName = new ComponentName(this, MyJS.class);
 *         JobInfo jobInfo = new JobInfo.Builder(OUR_JOB_ID, componentName)
 *                 .setExtras(extras)
 *                 .setPeriodic(ONE_DAY_INTERVAL)
 *                 .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
 *                 .build();
 *
 *         // Schedule the Job
 *         JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
 *         jobScheduler.schedule(jobInfo);
 *     }
 */
public class MyJobScheduler extends JobService {

    private static final String TAG = "MyJobScheduler";

    @Override
    public boolean onStartJob(JobParameters params) {
        // With Handler
        HandlerThread handlerThread = new HandlerThread("MyJobBackgroundThread");
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // Our code goes here
                // Our code goes here
                // Our code goes here
                jobFinished(params, false);
            }
        });

        // With AsyncTask
        AsyncTask<JobParameters, Void, Void> task = new AsyncTask<JobParameters, Void, Void>() {
            @Override
            protected Void doInBackground(JobParameters... jobParameters) {
                JobParameters currentParams = jobParameters[0];
                String passedData = currentParams.getExtras().getString(EXTRA_DATA);
                // Our code goes here
                // Our code goes here
                // Our code goes here
                Log.d(TAG, "doInBackground: Thread: " + Thread.currentThread().getName());
                Log.d(TAG, "doInBackground: passedData: " + passedData);

                jobFinished(currentParams, false);
                return null;
            }
        };

        task.execute(params);


        // On Main Thread
        Log.d(TAG, "onStartJob: ");
        try {
            // Our code goes here
        } catch (Exception e) {
            // Errors appears
        } finally {
            // Stop our job, and reschedule it or not.
            jobFinished(params, false);
        }
        // Our code goes here
        // Our code goes here
        // Our code goes here

        // To let the android knows our job is done, and we don't want to reschedule it
        jobFinished(params, false);

        return false;   // in main thread return false, otherwise true
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }
}
