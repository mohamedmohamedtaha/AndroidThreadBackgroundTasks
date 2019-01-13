package com.mzdhr.androidthreadbackgroundtasks.pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.Constant;
import com.mzdhr.androidthreadbackgroundtasks.database.AppDatabase;
import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;

import org.parceler.Parcels;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyThreadPoolRunnable implements Runnable{

    private static final String TAG = "MyThreadPoolRunnable";
    private int mStartAt;
    private int mChunkSize;
    private AppDatabase mAppDatabase;
    private WeakReference<Handler> mMainThreadHandler;

    public MyThreadPoolRunnable(int startAt, int chunkSize, AppDatabase appDatabase, Handler mainThreadHandler) {
        mStartAt = startAt;
        mChunkSize = chunkSize;
        mAppDatabase = appDatabase;
        mMainThreadHandler = new WeakReference<>(mainThreadHandler);
    }


    @Override
    public void run() {
        // Query the database, and parcel the result.
        ArrayList<NameEntity> names = new ArrayList<>(mAppDatabase.getNameDao().getNamesBetween(mStartAt, mChunkSize));

        for (int i = 0; i < names.size(); i++) {
            Log.d(TAG, "handleMessage: " + names.get(i).getName() + " - " + names.get(i).getNumber() + " - " + names.get(i).getId() + " | From Thread -> " + Thread.currentThread().getName());
        }

        Parcelable wrappedNames = Parcels.wrap(names);

        // Put the result into bundle.
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.DATABASE_MULTI_QUERY_RESULT, wrappedNames);

        // Create message object and Put that bundle in it.
        Message message = Message.obtain(null, Constant.DATABASE_MULTI_QUERY_TASK);
        message.setData(bundle);

        // Send message to main thread handler
        mMainThreadHandler.get().sendMessage(message);
    }
}
