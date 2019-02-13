package com.mzdhr.androidthreadbackgroundtasks.task;

import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * Created by MohammadL on 13/2/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyJobManager extends Worker {

    public MyJobManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String name = getInputData().getString("NAME_KEY");
        int age = getInputData().getInt("AGE_KEY", -1);

        // Our Code goes here
        String result = name + age;

        Data output = new Data.Builder()
                .putString("RESULT_KEY", result)
                .build();

        //return Result.failure();// Tells WorkManager to not do this task again.
        //return Result.retry();  // Tells WorkManager to do this task again.
        //return Result.success();

        return Result.success(output);
    }

}
