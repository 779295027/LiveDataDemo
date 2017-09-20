package com.sss.live.data.demo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sss.live.data.demo.db.entity.UserEntity;

import java.util.List;

/**
 * Created by sss on 2017/9/19.
 * 用户dao
 */
@Dao
public interface UserDao {
    /**
     * 查询所有的用户
     */
    @Query("SELECT * FROM user")
    List<UserEntity> getAll();
    /**
     * 查询所有的用户
     */
    @Query("SELECT * FROM user limit 0,1 ")
    LiveData<UserEntity> getTopOne();
    /**
     * 根据ID查询某个用户
     *
     * @param userIds 用户ID
     * @return 用户
     */
    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    List<UserEntity> loadAllByIds(int[] userIds);

    /**
     * 模糊查询某个用户
     *
     * @param first 名
     * @param last  姓
     * @return 用户
     */
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    UserEntity findByName(String first, String last);

    /**
     * 批量添加用户
     *
     * @param userEntities 要添加用户
     */
    @Insert
    void insertAll(UserEntity... userEntities);

    /**
     * 删除某个用户
     *
     * @param userEntity 用户
     */
    @Delete
    void delete(UserEntity userEntity);
}
