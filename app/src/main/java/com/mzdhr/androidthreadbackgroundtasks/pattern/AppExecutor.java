package com.mzdhr.androidthreadbackgroundtasks.pattern;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 *
 * This class is used with class NameRepository.java
 */
public class AppExecutor {
    private static final Object LOCK = new Object();
    private static AppExecutor sInstance;
    private final Executor mDiskIO;

    public AppExecutor(Executor diskIO) {
        mDiskIO = diskIO;
    }

    public static AppExecutor getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new AppExecutor(Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO() {
        return mDiskIO;
    }

}
