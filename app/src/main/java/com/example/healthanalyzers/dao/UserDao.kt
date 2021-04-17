package com.example.healthanalyzers.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.healthanalyzers.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User): String

    // 查询某个用户的个人信息
    @Query("SELECT * FROM User WHERE username = :username")
    fun queryInformation(username: String): List<User>

    // 用户注销
    @Query("DELETE FROM User WHERE username = :username")
    fun deleteByUsernanme(username: String): Boolean
}