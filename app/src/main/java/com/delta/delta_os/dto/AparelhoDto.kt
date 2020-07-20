package com.delta.delta_os.dto

import android.os.Build
import androidx.annotation.RequiresApi
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.util.Util
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

class AparelhoDto {
    var id:Int?=null;
    var idServidor:Int?=null;
    var nome:String?=null;
    var modelo:String?=null;
    var serial:String?=null;
    var pronto:String?=null;
    var idCliente:Int?=null;
    var autorizado:String?=null;
    var garantia:String?=null;
    var entregue:String?=null;
    var defeito_obs:String?=null;

    var dataEntrada:String?=null;

    var dataSaida: String?=null;
    var valor:Double?=null;
    var devolucao:String?=null;
    var uuidCliente:String?=null;

    @RequiresApi(Build.VERSION_CODES.O)
    fun build(aparelho: Aparelho):AparelhoDto{
        var aparelhoDto = AparelhoDto()
        aparelhoDto.id = aparelho.idServidor

        aparelhoDto.nome = aparelho.nome
        aparelhoDto.modelo = aparelho.modelo
        aparelhoDto.serial = aparelho.serial
        aparelhoDto.idCliente = aparelho.idCliente
        aparelhoDto.autorizado = aparelho.autorizado
        aparelhoDto.garantia = aparelho.garantia
        aparelhoDto.entregue = aparelho.entregue
        aparelhoDto.defeito_obs =aparelho.defeitoObs
        aparelhoDto.dataEntrada = aparelho.dataEntrada
        aparelhoDto.dataSaida = aparelho.dataSaida
        aparelhoDto.valor = aparelho.valor
        aparelhoDto.devolucao = aparelho.devolucao
        aparelhoDto.uuidCliente = aparelho.uuidCliente
        return aparelhoDto
    }



}