package com.example.muestraantonelli.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.muestraantonelli.entity.UsersModel

@Dao
interface UserDao {
    @Query("SELECT * FROM usersmodel")
    fun getAll(): List<UsersModel>

    @Query("SELECT * FROM usersmodel WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UsersModel>

    @Query("SELECT * FROM usersmodel WHERE name LIKE :name " + "LIMIT 1")
    fun findByName(name: String): UsersModel

    @Insert
    fun insertAll(vararg usersmodel: UsersModel)

    @Delete
    fun delete(usersmodel: UsersModel)
}