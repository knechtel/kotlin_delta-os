package com.delta.delta_os.dto

import com.delta.delta_os.bean.Aparelho
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
    var dataEntrada:Date?=null;
    var dataSaida: Date?=null;
    var valor:Double?=null;
    var devolucao:String?=null;

    fun build(aparelho: Aparelho):AparelhoDto{
        var aparelhoDto = AparelhoDto()
        aparelhoDto.id = aparelho.id
        aparelhoDto.idServidor =aparelho.idServidor
        aparelhoDto.nome = aparelho.nome
        aparelhoDto.modelo = aparelho.modelo
        aparelhoDto.serial = aparelho.serial
        aparelhoDto.idCliente = aparelho.idCliente
        return aparelhoDto
    }

}