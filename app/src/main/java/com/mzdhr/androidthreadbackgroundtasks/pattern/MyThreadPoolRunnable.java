package com.mzdhr.androidthreadbackgroundtasks.pattern;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.mzdhr.androidthreadbackgroundtasks.Constant;
import com.mzdhr.androidthreadbackgroundtasks.database.AppDatabase;
import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class MyThreadPoolRunnable implements Runnable{

    private static final String TAG = "MyThreadPoolRunnable";
    private int mStart;
    private int mEnd;
    private AppDatabase mAppDatabase;
    private WeakReference<Handler> mMainThreadHandler;

    public MyThreadPoolRunnable(int start, int end, AppDatabase appDatabase, Handler mainThreadHandler) {
        mStart = start;
        mEnd = end;
        mAppDatabase = appDatabase;
        mMainThreadHandler = new WeakReference<>(mainThreadHandler);
    }


    @Override
    public void run() {

        Message message = Message.obtain(null, Constant.RESULT_THREAD_POOL_KEY); // FIXME: 08/1/2019 naming -> RESULT_THREAD_POOL_KEY

        ArrayList<NameEntity> names = new ArrayList<>(mAppDatabase.getNameDao().getNamesBetween(mStart, mEnd));
        Bundle bundle = new Bundle();
       // bundle.putStringArrayList("RESULT_THREAD_POOL_KEY", names);
       // bundle
    }
}
