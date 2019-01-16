package com.mzdhr.androidthreadbackgroundtasks.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.Constant;

/**
 * Created by MohammadL on 16/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyBoundService extends Service {

    private static final String TAG = "MyBoundService";
    private MyBinder mMyBinder = new MyBinder();
    private int mResult = -1;

    // Our Binder Class
    public class MyBinder extends Binder {
        MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMyBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: Called.");

        int firstNumber = intent.getIntExtra(Constant.FIRST_NUMBER, -1);
        int secondNumber = intent.getIntExtra(Constant.SECOND_NUMBER, -2);

        int resultNumber = firstNumber + secondNumber;
        mMyBinder.getService().setResult(resultNumber);

        return Service.START_NOT_STICKY;
    }

    private void setResult(int resultNumber) {
        mResult = resultNumber;
    }

    public int getResult() {
        return mResult;
    }



}
