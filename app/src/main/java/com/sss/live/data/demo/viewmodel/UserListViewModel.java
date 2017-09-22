package com.sss.live.data.demo.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.util.Log;

import com.sss.live.data.demo.db.DatabaseCreator;
import com.sss.live.data.demo.db.entity.UserEntity;

import java.util.List;

/**
 * Created by sss on 2017/9/22.
 */

public class UserListViewModel extends AndroidViewModel {

    private LiveData<List<UserEntity>> listLiveData;
    private DatabaseCreator databaseCreato;

    public UserListViewModel(Application application) {
        super(application);
        if (databaseCreato == null)
            databaseCreato = DatabaseCreator.getInstance();
        listLiveData = Transformations.switchMap(databaseCreato.getmIsDatabaseCreated(), new Function<Boolean, LiveData<List<UserEntity>>>() {
            @Override
            public LiveData<List<UserEntity>> apply(Boolean aBoolean) {
                Log.e("--->", "--->" + "数据库是否创建完成？");
                if (!Boolean.TRUE.equals(aBoolean)) {
                    Log.e("--->", "--->" + "否");
                    return new MediatorLiveData<>();
                } else {
                    Log.e("--->", "--->" + "是");
                    // 数据库创建完，取库中第一条数据
                    return databaseCreato.getDatabase().userDao().getAll();
                }
            }
        });
        databaseCreato.create(application);
    }


    public LiveData<List<UserEntity>> getListLiveData() {
        return listLiveData;
    }
}
