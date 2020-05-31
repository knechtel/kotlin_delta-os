package com.delta.delta_os.util

import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Cliente
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface ClienteService {
    @GET("list")
    fun getClientes(): Call<List<Cliente>>
    @GET("listAparelho")
    fun getAparelho(): Call<List<Aparelho>>
}