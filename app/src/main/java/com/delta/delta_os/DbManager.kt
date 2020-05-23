package com.hussein.startup

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast
import com.delta.delta_os.Cliente
import kotlinx.android.synthetic.main.activity_main.*


class  DbManager{

    val dbName="loja"
    val dbTable="Cliente"
    val colID="ID"
    val colNome="nome"
    val colCpf="cpf"
    val colEndereco="endereco"
    val colTelefone="telefone"
    val colEmail="email"
    val dbVersion=1
    //CREATE TABLE IF NOT EXISTS MyNotes (ID INTEGER PRIMARY KEY,title TEXT, Description TEXT);"
    val sqlCreateTable="CREATE TABLE IF NOT EXISTS "+ dbTable +" ("+ colID +" INTEGER PRIMARY KEY,"+
            colNome + " TEXT," +
            colCpf + " TEXT,"+
            colEndereco + " TEXT,"+
            colTelefone + " TEXT,"+
            " "+ colEmail +" TEXT);"
    var sqlDB:SQLiteDatabase?=null

    constructor(context:Context){
        var db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase

    }


    inner class  DatabaseHelperNotes:SQLiteOpenHelper{
         var context:Context?=null
        constructor(context:Context):super(context,dbName,null,dbVersion){
            this.context=context
        }
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context," database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
        }

    }

    fun LoadQuery():ArrayList<Cliente>{
        var listCliente = ArrayList<Cliente>()
        val projections= arrayOf("ID","nome","cpf","endereco","telefone","email")
        val cursor= sqlDB?.rawQuery("select * from cliente  ",null)

        if (cursor != null) {
            if(cursor.moveToFirst()){

                do{
                    val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome=cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf=cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco=cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone=cursor.getString(cursor.getColumnIndex("telefone"))
                    val email=cursor.getString(cursor.getColumnIndex("email"))
                    listCliente.add( Cliente(ID,nome,cpf,endereco,telefone,email))

                }while (cursor.moveToNext())
            }
        }
        cursor!!.close()
     return listCliente;


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

}