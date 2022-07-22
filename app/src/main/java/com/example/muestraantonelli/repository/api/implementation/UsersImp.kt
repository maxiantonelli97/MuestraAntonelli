package com.example.muestraantonelli.repository.api.implementation

import com.example.muestraantonelli.entity.UsersModel
import com.example.muestraantonelli.repository.api.UsersApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsersImp {

    private  fun getRetrofit() : Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .build()
    }

    fun getUsers(): Call<List<UsersModel>> {

        return getRetrofit().create(UsersApi::class.java).getAPI()
    }

}