package com.mzdhr.androidthreadbackgroundtasks.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.Constant;

/**
 * Created by MohammadL on 16/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super("MyIntentService");   // This gonna be our Thread name.
        Log.d(TAG, "MyIntentService: Called.");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: Called.");

        int firstNumber = intent.getIntExtra(Constant.FIRST_NUMBER, -1);
        int secondNumber = intent.getIntExtra(Constant.SECOND_NUMBER, -2);

        int resultNumber = firstNumber + secondNumber;
        Log.d(TAG, "onStartCommand: Result -> " + resultNumber);


        // Send to Activity by ResultReceiver
        ResultReceiver resultReceiver = intent.getParcelableExtra(Constant.RECEIVE_RESULT_KEY);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.RESULT_NUMBER, String.valueOf(resultNumber) + " - From IntentService");
        resultReceiver.send(88, bundle);    // 88 our code for this task
    }

}
