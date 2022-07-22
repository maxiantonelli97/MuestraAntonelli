package com.example.muestraantonelli.repository.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.muestraantonelli.entity.UsersModel

@Database(entities = [UsersModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}