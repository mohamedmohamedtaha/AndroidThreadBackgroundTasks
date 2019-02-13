package com.mzdhr.androidthreadbackgroundtasks.task;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.mzdhr.androidthreadbackgroundtasks.pattern.AppExecutor;

/**
 * Created by MohammadL on 13/2/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyJobDispatcher extends JobService {
    @Override
    public boolean onStartJob(final JobParameters job) {
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                // Our code goes here
                // Our code goes here
                // Our code goes here
                jobFinished(job, false);
            }
        });
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
