package com.delta.delta_os.service


import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import com.delta.delta_os.dto.AparelhoDto
import com.delta.delta_os.util.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList


class AparelhoServiceSync {

    fun atualizaAparelho(): List<Aparelho> {

        var db = DbManager(Session.context)
        var listAparelho: List<Aparelho> = db.LoadQueryAparelhoByIDServidorZero(0)
        var listDTO = ArrayList<AparelhoDto>()
        listAparelho.forEach {
            listDTO.add(AparelhoDto().build(it))
        }
        println("olhar aquiiii ->>>>>>>>>   "+listAparelho.size)
       // println(listAparelho[0].nome+" ----  "+listAparelho[0].id.toString())
        val call = RetrofitInitializer().aparelhoService().addAparelho(listDTO)
        call.enqueue(
            object : Callback<List<AparelhoDto>?> {
                override fun onResponse(
                    call: Call<List<AparelhoDto>?>?,
                    response: Response<List<AparelhoDto>?>?
                ) {
                    response?.body()?.let {
                        var listClienteSync: List<AparelhoDto> = it;

                        listDTO.forEach {apDto ->

                            listAparelho.forEach { aparelho ->

                                var dbAparelho = DbManager(Session.context)
                                dbAparelho.updateAparelho(aparelho.id!!.toLong(),apDto.id!!.toLong());
                            }
                        }

                    }

                }

                override fun onFailure(call: Call<List<AparelhoDto>?>?, t: Throwable?) {

                }
            }
        )
        return listAparelho;

    }
}