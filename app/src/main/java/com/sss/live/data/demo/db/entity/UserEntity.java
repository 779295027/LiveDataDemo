package com.sss.live.data.demo.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.sss.live.data.demo.model.User;

/**
 * Created by sss on 2017/9/19.
 * 当一个实体类注解上@Entity，并在被注解@Database的类中引用时，Room会在数据库中为该实体类创建一个数据库表
 */
@Entity(tableName = "user")
public class UserEntity implements User {
    /**
     * 用户编号
     */
    @PrimaryKey
    private int id;

    /**
     * 名
     */
    @ColumnInfo(name = "first_name")
    private String firstName;

    /**
     * 姓
     */
    @ColumnInfo(name = "last_name")
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
