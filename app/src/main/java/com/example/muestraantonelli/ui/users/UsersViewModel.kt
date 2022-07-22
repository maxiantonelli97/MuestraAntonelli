package com.example.muestraantonelli.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muestraantonelli.entity.UsersModel
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.example.muestraantonelli.repository.api.implementation.UsersImp
import com.example.muestraantonelli.repository.dao.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(): ViewModel() {

    private val _users = MutableLiveData<ArrayList<UsersModel>>()
    val users: LiveData<ArrayList<UsersModel>> get() = _users
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getUsers().enqueue(object : Callback<List<UsersModel>> {
                override fun onFailure(call: Call<List<UsersModel>>, t: Throwable) {
                    Log.e("Error-Invoke-API", t.message.toString())
                    _error.postValue(true)
                }

                @SuppressLint("WrongConstant")
                override fun onResponse(
                    call: Call<List<UsersModel>>,
                    response: Response<List<UsersModel>>
                ) {

                    if (response.body() != null) {
                        val data = response.body()
                        _users.postValue(data!! as ArrayList<UsersModel>)
                        _error.postValue(false)
                    } else {
                        _error.postValue(true)
                    }
                }
            })
        }
    }

    fun eliminarItemDB(user : UsersModel, context : Context){
        val db =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-users"
            ).allowMainThreadQueries()
                .build()
        val userDao = db.userDao()
        userDao.delete(user)
    }

    fun obtenerUsuariosDB(context: Context): List<UsersModel> {
        val db =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-users"
            ).allowMainThreadQueries()
                .build()
        val userDao = db.userDao()
        return userDao.getAll()
    }

    private fun getUsers() : Call<List<UsersModel>> {

        val api = UsersImp()

        return api.getUsers()
    }
}