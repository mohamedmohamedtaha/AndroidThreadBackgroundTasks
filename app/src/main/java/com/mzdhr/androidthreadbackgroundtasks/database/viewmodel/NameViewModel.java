package com.mzdhr.androidthreadbackgroundtasks.database.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.mzdhr.androidthreadbackgroundtasks.database.entity.NameEntity;
import com.mzdhr.androidthreadbackgroundtasks.database.repository.NameRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MohammadL on 08/1/2019
 * Contact me at mmlaif@gmail.com
 */
public class NameViewModel extends AndroidViewModel {
    private NameRepository mRepository;
    private LiveData<List<NameEntity>> mNames;

    public NameViewModel(@NonNull Application application) {
        super(application);
        mRepository = NameRepository.getInstance(application);
        mNames = mRepository.getNames();
    }

    public LiveData<List<NameEntity>> getNames() {
        return mNames;
    }

    public ArrayList<NameEntity> getNames(int start, int end) {
        return mRepository.getNames(start, end);
    }

    public void insertName(NameEntity nameEntity) {
        mRepository.insert(nameEntity);
    }

    public Long insertNameRetrieveColumnId(NameEntity nameEntity) {
        return mRepository.insertRetrieveColumnId(nameEntity);
    }

    public void deleteName(NameEntity nameEntity) {
        mRepository.delete(nameEntity);
    }

    public void deleteNames() {
        mRepository.deleteAll();
    }

}
