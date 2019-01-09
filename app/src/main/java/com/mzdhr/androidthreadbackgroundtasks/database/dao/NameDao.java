package com.mzdhr.androidthreadbackgroundtasks.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
@Dao
public interface NameDao {
    @Query("SELECT * FROM names ORDER BY name_id DESC")
    LiveData<List<NameEntity>> getAllNames();

    @Query("SELECT * FROM names WHERE name_id = :id")
    LiveData<NameEntity> getNameById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertName(NameEntity nameEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertAndReturnColumnId(NameEntity nameEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateName(NameEntity nameEntity);

    @Delete
    void deleteName(NameEntity nameEntity);

    @Query("DELETE FROM names")
    void deleteAll();

    // More about LIMIT at -> http://www.sqlitetutorial.net/sqlite-limit/
    @Query("SELECT * FROM names ORDER BY name_id DESC LIMIT :start , :end")
    ArrayList<NameEntity> getNamesBetween(int start, int end);
}
