package com.delta.delta_os.util

import com.delta.delta_os.bean.Cliente
import okhttp3.ResponseBody
import retrofit2.Call

import retrofit2.http.POST




interface ApiDelta {
    @POST("http://localhost:7070/listCliente?")
    fun getClientes(): Call<ResponseBody>

}