package com.example.muestraantonelli.repository.api

import com.example.muestraantonelli.entity.UsersModel
import retrofit2.Call
import retrofit2.http.GET

interface UsersApi {

    @GET("users")
    fun getAPI() : Call<List<UsersModel>>
}