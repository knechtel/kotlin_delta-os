package com.delta.delta_os.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Cliente


class  DbManager{

    val dbName="loja"
    val dbTable="Cliente"
    val colID="ID"
    val colNome="nome"
    val colCpf="cpf"
    val colEndereco="endereco"
    val colTelefone="telefone"
    val colEmail="email"
    val dbTableAparelho="Aparelho"
    val idAparelho ="id"
    val nomeAaparelho="nome"
    val modelo = "modelo"
    val serial = "serial"
    val pronto = "pronto"
    val idCliente = "idCliente"
    val autorizado = "autorizado"
    val garantia = "garantia"
    val entregue = "entregue"
    val defeito_obs = "defeito_obs"
    val dataEntrada = "dataEntrada"
    val dataSaida = "dataSaida"
    val valor = "valor"
    val dbVersion=13
    //CREATE TABLE IF NOT EXISTS MyNotes (ID INTEGER PRIMARY KEY,title TEXT, Description TEXT);"
    val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dbTable +" ("+ colID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            colNome + " TEXT, " +
            colCpf + " TEXT, "+
            colEndereco + " TEXT, "+
            colTelefone + " TEXT, "+
            " "+ colEmail +" TEXT);"
    val sqlCreateTable2 =
    " CREATE TABLE IF NOT EXISTS "+dbTableAparelho+" ("+
            idAparelho+" INTEGER PRYMARY KEY, "+
            nomeAaparelho+" TEXT NULL, "+
            modelo+" TEXT NULL, "+
            serial+" TEXT NULL, "+
            pronto+" TEXT NULL, "+
            idCliente+" INTEGER, "+
            autorizado+" TEXT NULL, "+
            garantia+" TEXT NULL, "+
            entregue+" TEXT NULL, "+
            defeito_obs+" TEXT NULL, "+
            dataEntrada+" TEXT NULL, "+
            dataSaida+" TEXT NULL, "+
            valor+ " REAL);"
    var sqlDB:SQLiteDatabase?=null

    constructor(context:Context){
        var db=DatabaseHelperNotes(context)

        sqlDB=db.writableDatabase

    }


    inner class  DatabaseHelperNotes:SQLiteOpenHelper{
         var context:Context?=null

        constructor(context:Context):super(context,dbName,null,13){
            this.context=context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            p0!!.execSQL(sqlCreateTable2)
            Toast.makeText(this.context," database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
            p0!!.execSQL("Drop table IF EXISTS $dbTableAparelho")
            p0.execSQL(sqlCreateTable);
            p0.execSQL(sqlCreateTable2);
        }

    }

    fun LoadQuery():ArrayList<Cliente>{
        var listCliente = ArrayList<Cliente>()
        val projections= arrayOf("ID","nome","cpf","endereco","telefone","email")
        val cursor= sqlDB?.rawQuery("select * from Cliente  ",null)

        if (cursor != null) {
            if(cursor.moveToFirst()){

                do{
                    val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome=cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf=cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco=cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone=cursor.getString(cursor.getColumnIndex("telefone"))
                    val email=cursor.getString(cursor.getColumnIndex("email"))
                    listCliente.add(
                        Cliente(
                            ID,
                            nome,
                            cpf,
                            endereco,
                            telefone,
                            email
                        )
                    )

                }while (cursor.moveToNext())
            }
        }
        cursor!!.close()
     return listCliente;


    }
    fun LoadQueryAparelho():ArrayList<Aparelho>{
        var listAparelho = ArrayList<Aparelho>()

        val cursor= sqlDB?.rawQuery("select * from Aparelho  ",null)

        if (cursor != null) {
            if(cursor.moveToFirst()){
                var pronto="";
                do{
                    val ID=cursor.getInt(cursor.getColumnIndex("id"))
                    val nome=cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo=cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial=cursor.getString(cursor.getColumnIndex("serial"))
                        pronto="pronto";
                    val idCliente=cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado="autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia="NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue="NAO_ENTREGUE"//cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs="OBS"//cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada="dataEntrada"//cursor.getString(cursor.getColumnIndex("dataEntrada"))
                   // val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                  //  val valor=cursor.getDouble(cursor.getColumnIndex("valor").toDouble().toInt())
                    listAparelho.add(
                      Aparelho(ID,nome,modelo,serial,pronto,idCliente,autorizado,garantia,entregue,defeito_obs,
                      dataEntrada,"",8.0)
                    )

                }while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listAparelho;


    }

    fun LoadQueryAparelhoByOS(ID:Long):ArrayList<Aparelho>{
        var listAparelho = ArrayList<Aparelho>()

        val cursor= sqlDB?.rawQuery("select * from Aparelho  where  idCliente = "+ID+" ;" ,null);

        if (cursor != null) {
            if(cursor.moveToFirst()){
                var pronto="";
                do{
                    val ID=cursor.getInt(cursor.getColumnIndex("id"))
                    val nome=cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo=cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial=cursor.getString(cursor.getColumnIndex("serial"))
                    pronto="pronto";
                    val idCliente=cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado="autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia="NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue="NAO_ENTREGUE"//cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs="OBS"//cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada="dataEntrada"//cursor.getString(cursor.getColumnIndex("dataEntrada"))
                    // val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                    //  val valor=cursor.getDouble(cursor.getColumnIndex("valor").toDouble().toInt())
                    listAparelho.add(
                        Aparelho(ID,nome,modelo,serial,pronto,idCliente,autorizado,garantia,entregue,defeito_obs,
                            dataEntrada,"",8.0)
                    )

                }while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listAparelho;


    }

    fun Insert(values:ContentValues):Long{

        val ID= sqlDB!!.insert(dbTable,"",values)
        return ID
    }
    fun  Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sorOrder:String):Cursor{

        val qb=SQLiteQueryBuilder()
        qb.tables=dbTable
        val cursor=qb.query(sqlDB,projection,selection,selectionArgs,null,null,sorOrder)
        return cursor
    }
    fun Delete(selection:String,selectionArgs:Array<String>):Int{

        val count=sqlDB!!.delete(dbTable,selection,selectionArgs)
        return  count
    }

    fun Update(values:ContentValues,selection:String,selectionargs:Array<String>):Int{

        val count=sqlDB!!.update(dbTable,values,selection,selectionargs)
        return count
    }
    fun InsertAparelho(values:ContentValues):Long{

        val ID= sqlDB!!.insert(dbTableAparelho,"",values)
        return ID
    }

}


