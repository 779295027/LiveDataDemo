package com.sss.live.data.demo.db;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.sss.live.data.demo.db.entity.UserEntity;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by sss on 2017/9/19.
 * 用于创建Database的工具类
 */
public class DatabaseCreator {
    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "live-data-demo-db";
    private static DatabaseCreator sInstance;
    /**
     * 是否已经初始化
     */
    private final AtomicBoolean aBoolean = new AtomicBoolean(true);

    private AppDatabase appDatabase;


    /**
     * 用于创建单例，锁住这个对象，防止线程征用
     */
    private static final Object LOCK = new Object();

    public synchronized static DatabaseCreator getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new DatabaseCreator();
                }
            }
        }
        return sInstance;
    }

    @Nullable
    public AppDatabase getDatabase() {
        return appDatabase;
    }


    /**
     * 创建数据库实例，即AppDatabase的实例
     *
     * @param context
     */
    public void create(Context context) {
        // 如果aBoolean为true,那么赋值为false，并返回true，表示没有初始化数据库
        if (!aBoolean.compareAndSet(true, false)) {
            return; // 表示已经初始化过数据库
        }

        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                Context context = contexts[0].getApplicationContext();
                context.deleteDatabase(DATABASE_NAME);
                appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
                try {
                    Thread.sleep(4000);
                } catch (Exception ignored) {
                }
                UserEntity userEntity = new UserEntity();
                userEntity.setFirstName("名");
                userEntity.setLastName("姓");
                appDatabase.beginTransaction();
                try {
                    appDatabase.userDao().insertAll(userEntity);
                    appDatabase.setTransactionSuccessful();
                } finally {
                    appDatabase.endTransaction();
                }
                return null;
            }
        }.execute(context.getApplicationContext());
    }
}
