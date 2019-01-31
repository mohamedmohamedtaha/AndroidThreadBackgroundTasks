package com.mzdhr.androidthreadbackgroundtasks.database.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.database.AppDatabase;
import com.mzdhr.androidthreadbackgroundtasks.database.dao.NameDao;
import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;
import com.mzdhr.androidthreadbackgroundtasks.pattern.AppExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class NameRepository {
    private static final String TAG = "NameRepository";
    private static NameRepository sInstance;
    private final NameDao mNameDao;
    private LiveData<List<NameEntity>> mNames;

    private NameRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mNameDao = db.getNameDao();
        mNames = mNameDao.getAllNames();
    }

    public static NameRepository getInstance(Application application) {
        if (sInstance == null) {
            synchronized (NameRepository.class) {
                if (sInstance == null) {
                    sInstance = new NameRepository(application);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<NameEntity>> getNames() {
        return mNames;
    }


    public ArrayList<NameEntity> getNames(int start, int end) {
        // TODO: 08/1/2019 Executors.newFixedThreadPool();

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        Log.d(TAG, "getNames: Cores Numbers -> " + availableProcessors);


        // Convert List to ArrayList
        return new ArrayList<NameEntity>(mNameDao.getNamesBetween(start, end));
    }

    public LiveData<NameEntity> getNameById(final int name) {
        return mNameDao.getNameById(name);
    }

    public void insert(final NameEntity nameEntity) {
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNameDao.insertName(nameEntity);
            }
        });
    }

    public void insertNoRetrieveColumnId(final NameEntity nameEntity) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mNameDao.insertName(nameEntity);
            }
        };

        executorService.execute(runnable);
    }

    public Long insertRetrieveColumnId(final NameEntity nameEntity) {
        // Prepare column id
        Long insertedColumnId = -1L;

        // Create the Callable object that carry our code to the executor
        Callable<Long> callable = new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return mNameDao.insertAndReturnColumnId(nameEntity);
            }
        };

        // Create an executor
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Create a Future object
        Future<Long> future = executorService.submit(callable);

        // Retrieve the result into our future object, within 1 second or cancel it.
        // .get() -> Blocks the Thread, so we should put a max duration for it.
        // We can use future.isDone() to know if the task is done or not before using future.get().
        try {
            insertedColumnId = future.get(1, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // Return the column id
        return insertedColumnId;
    }

    /**
     * Should be examples for each:
     *         Executors.newScheduledThreadPool();
     *         Executors.newFixedThreadPool();          DONE
     *         Executors.newSingleThreadExecutor();     DONE
     *         Executors.newCachedThreadPool(); // Executors.newSingleThreadScheduledExecutor();
     * @param nameEntity
     */
    public void update(final NameEntity nameEntity) {
        AppExecutor.getInstance().diskIO().execute( new Runnable(){
            @Override
            public void run() {
                mNameDao.updateName(nameEntity);
            }
        });
    }

    public void delete(final NameEntity nameEntity) {
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNameDao.deleteName(nameEntity);
            }
        });
    }

    public void deleteAll() {
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mNameDao.deleteAll();
            }
        });
    }

}
