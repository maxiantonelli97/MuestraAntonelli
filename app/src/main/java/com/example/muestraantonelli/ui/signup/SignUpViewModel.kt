package com.example.muestraantonelli.ui.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.muestraantonelli.entity.UsersModel
import com.example.muestraantonelli.repository.dao.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel() {


    fun registrarUsuario(context: Context, users : UsersModel) : Boolean{
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-users"
        ).allowMainThreadQueries().build()
        val userDao = db.userDao()
        return try{
            userDao.insertAll(users)
            true
        }catch(exp : Exception){
            false
        }
    }
}