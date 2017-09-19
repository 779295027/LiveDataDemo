package com.sss.live.data.demo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sss.live.data.demo.db.dao.UserDao;
import com.sss.live.data.demo.db.entity.UserEntity;

/**
 * Created by sss on 2017/9/19.
 * 注意：实例化AppDatabase对象时，应遵循单例设计模式，因为每个RoomDatabase实例都相当耗费资源，并且您很少需要访问多个实例
 */
@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
