package com.example.muestraantonelli.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsersModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "email") val email: String = "",
    @ColumnInfo(name = "phone") val phone: String = "",
    @ColumnInfo(name = "local") val local: Boolean = false,
)

