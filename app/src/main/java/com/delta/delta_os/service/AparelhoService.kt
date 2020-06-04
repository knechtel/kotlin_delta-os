package com.delta.delta_os.service

import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.dto.AparelhoDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AparelhoService {
    @POST("doCreateAparelho")
    fun addAparelho(@Body listAparelho:List<AparelhoDto>) : Call<List<AparelhoDto>>
}