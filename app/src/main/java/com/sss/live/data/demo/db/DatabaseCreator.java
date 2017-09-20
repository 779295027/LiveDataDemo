package com.sss.live.data.demo.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

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
    /**
     * 数据库创建是否完成
     */
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
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


    public LiveData<Boolean> getmIsDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    /**
     * 创建数据库实例，即AppDatabase的实例
     *
     * @param context 上下文
     */
    public void create(Context context) {
        Log.e("DatabaseCreator", "开始运行创建数据库方法");

        // 如果aBoolean为true,那么赋值为false，并返回true，表示没有初始化数据库
        if (!aBoolean.compareAndSet(true, false)) {
            return; // 表示已经初始化过数据库
        }

        Log.e("DatabaseCreator", "给mIsDatabaseCreated赋值，数据库还未创建成功（主要做初始化这个对象用）");
        mIsDatabaseCreated.setValue(false);
        new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
                Log.e("DatabaseCreator", "启动Background，开始创建数据库");
                Context context = contexts[0].getApplicationContext();
                // 重置数据库，删除之前的数据库
                context.deleteDatabase(DATABASE_NAME);
                // 创建数据库
                appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
                try {
                    // 沉睡4秒，显得创建非常耗时，实际开发中此行无用
                    Thread.sleep(2000);
                } catch (Exception ignored) {
                }
                // 往数据库中添加假数据
                appDatabase = initData(appDatabase);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Log.e("DatabaseCreator", "给mIsDatabaseCreated赋值，数据库创建成功");
                // 数据库已创建,通知页面可以调用数据
                mIsDatabaseCreated.setValue(true);
            }
        }.execute(context.getApplicationContext());
    }

    private AppDatabase initData(AppDatabase appDatabase) {
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
        return appDatabase;
    }

}
