package com.delta.delta_os.util


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.0.104:6060")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun noteService() = retrofit.create(ClienteService::class.java)
}