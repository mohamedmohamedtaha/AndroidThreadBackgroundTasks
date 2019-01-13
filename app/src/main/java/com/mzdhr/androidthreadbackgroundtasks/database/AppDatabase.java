package com.mzdhr.androidthreadbackgroundtasks.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mzdhr.androidthreadbackgroundtasks.database.dao.NameDao;
import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;

/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
@Database(entities = {NameEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "namesdb";
    private static AppDatabase sInstance;


    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "getInstance: Creating a new database instance");
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME
                )

                        .addCallback(sRoomDatabaseCallback) // populate database
                        .build();
            }
        }
        Log.d(TAG, "getInstance: Getting the database instance, no need to recreated it.");
        return sInstance;
    }

    public abstract NameDao getNameDao();

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // new PopulateDbAsync(sInstance).execute();   // todo: use executors and another example for asynctask
            AsyncTask<Void, Void, Void> aa = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    NameDao nameDao = sInstance.getNameDao();
                    nameDao.insertName(new NameEntity("Mohammad1", "0555555551"));
                    nameDao.insertName(new NameEntity("Mohammad2", "0555555552"));
                    nameDao.insertName(new NameEntity("Mohammad3", "0555555553"));
                    nameDao.insertName(new NameEntity("Mohammad4", "0555555554"));
                    nameDao.insertName(new NameEntity("Mohammad5", "0555555555"));
                    nameDao.insertName(new NameEntity("Mohammad6", "0555555556"));
                    nameDao.insertName(new NameEntity("Mohammad7", "0555555557"));
                    nameDao.insertName(new NameEntity("Mohammad8", "0555555558"));
                    nameDao.insertName(new NameEntity("Mohammad9", "0555555559"));
                    nameDao.insertName(new NameEntity("Mohammad10", "05555555510"));
                    return null;
                }
            };

            aa.execute();

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
