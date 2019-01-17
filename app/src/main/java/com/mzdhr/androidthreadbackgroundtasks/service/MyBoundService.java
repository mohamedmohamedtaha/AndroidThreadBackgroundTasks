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

        public MyBoundService getService() {
            return MyBoundService.this;
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Called.");

        int firstNumber = intent.getIntExtra(Constant.FIRST_NUMBER, -1);
        int secondNumber = intent.getIntExtra(Constant.SECOND_NUMBER, -2);

        int resultNumber = firstNumber + secondNumber;
        mMyBinder.getService().setResult(resultNumber);

        return mMyBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    private void setResult(int resultNumber) {
        mResult = resultNumber;
    }

    // Used to send bound
    public int getResult() {
        return mResult;
    }



}
