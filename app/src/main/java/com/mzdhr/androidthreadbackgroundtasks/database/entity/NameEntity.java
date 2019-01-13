package com.mzdhr.androidthreadbackgroundtasks.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
@Parcel(Parcel.Serialization.BEAN)
@Entity(tableName = "names")
public class NameEntity {
    // Fields
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "name_id")
    public int mId;
    public String mName;
    public String mNumber;

    // Room Constructor
    public NameEntity(int id, String name, String number) {
        mId = id;
        mName = name;
        mNumber = number;
    }

    // Constructor to create new object from this class
    @Ignore
    @ParcelConstructor
    public NameEntity(String name, String number) {
        mName = name;
        mNumber = number;
    }

    // Getter and Setter

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }
}
