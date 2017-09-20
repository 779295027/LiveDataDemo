package com.sss.live.data.demo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.core.util.Function;
import android.util.Log;

import com.sss.live.data.demo.db.DatabaseCreator;
import com.sss.live.data.demo.db.entity.UserEntity;


/**
 * Created by sss on 2017/9/20.
 */

public class UserViewModel extends AndroidViewModel {

    private LiveData<UserEntity> userLiveData;

    public UserViewModel(Application application) {
        super(application);
        final DatabaseCreator databaseCreato = DatabaseCreator.getInstance();
        LiveData<Boolean> databaseCreated = databaseCreato.getmIsDatabaseCreated();
        userLiveData = Transformations.switchMap(databaseCreated, new Function<Boolean, LiveData<UserEntity>>() {
            @Override
            public LiveData<UserEntity> apply(Boolean aBoolean) {
                Log.e("--->", "--->" + "数据库是否创建完成？");
                if (!Boolean.TRUE.equals(aBoolean)) {
                    Log.e("--->", "--->" + "否");
                    return new MediatorLiveData<>();
                } else {
                    Log.e("--->", "--->" + "是");
                    // 数据库创建完，取库中第一条数据
                    return databaseCreato.getDatabase().userDao().getTopOne();
                }

            }
        });

        // 创建数据库
        databaseCreato.create(application.getApplicationContext());

    }

    public LiveData<UserEntity> getUserLiveData() {
        return userLiveData;
    }
}
