package com.delta.delta_os.bean



class Aparelho {
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
    var defeitoObs:String?=null;
    var dataEntrada:String?=null;
    var dataSaida:String?=null;
    var valor:Double?=null;

    constructor(id:Int,nome:String,modelo:String,
                serial:String,pronto:String,idCliente:Int,
                autorizado:String,garantia:String,
                entregue:String, defeitoObs:String,
                dataEntrada:String,dataSaida:String,valor:Double,
                idServidor:Int
    ){
        this.id=id;
        this.nome=nome;
        this.modelo=modelo;
        this.serial=serial;
        this.pronto=pronto;
        this.idCliente=idCliente;
        this.autorizado=autorizado;
        this.garantia=garantia;
        this.entregue=entregue;
        this.defeitoObs=defeitoObs;
        this.dataEntrada=dataEntrada;
        this.dataSaida=dataSaida
        this.valor=valor;
        this.idServidor=idServidor;
    }
    constructor(){}

    //func dtoToBean():Aparelho
}