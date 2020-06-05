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


class DbManager {

    val dbName = "loja"
    val dbTable = "Cliente"
    val colID = "ID"
    val colIdServidor = "idServidor"
    val colNome = "nome"
    val colCpf = "cpf"
    val colEndereco = "endereco"
    val colTelefone = "telefone"
    val colEmail = "email"
    val dbTableAparelho = "Aparelho"
    val idAparelho = "id"
    val idServidor = "idServidor"
    val nomeAaparelho = "nome"
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
    val devolucao = "devolucao"
    val valor = "valor"
    val dbVersion = 64

    //CREATE TABLE IF NOT EXISTS MyNotes (ID INTEGER PRIMARY KEY,title TEXT, Description TEXT);"
    val sqlCreateTable =
        "CREATE TABLE IF NOT EXISTS " + dbTable + " (" + colID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                colIdServidor + " INTEGER, " +
                colNome + " TEXT, " +
                colCpf + " TEXT, " +
                colEndereco + " TEXT, " +
                colTelefone + " TEXT, " +
                " " + colEmail + " TEXT);"
    val sqlCreateTable2 =
        " CREATE TABLE IF NOT EXISTS " + dbTableAparelho + " (" +
                idAparelho + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                nomeAaparelho + " TEXT NULL, " +
                modelo + " TEXT NULL, " +
                serial + " TEXT NULL, " +
                pronto + " TEXT NULL, " +
                idCliente + " INTEGER, " +
                colIdServidor + " INTEGER, " +
                autorizado + " TEXT NULL, " +
                garantia + " TEXT NULL, " +
                entregue + " TEXT NULL, " +
                defeito_obs + " TEXT NULL, " +
                dataEntrada + " TEXT NULL, " +
                dataSaida + " TEXT NULL, " +
                devolucao + " TEXT NULL, " +
                valor + " REAL);"
    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        var db = DatabaseHelperNotes(context)

        sqlDB = db.writableDatabase

    }


    inner class DatabaseHelperNotes : SQLiteOpenHelper {
        var context: Context? = null

        constructor(context: Context) : super(context, dbName, null, 64) {
            this.context = context
        }

        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            p0!!.execSQL(sqlCreateTable2)
            Toast.makeText(this.context, " database is created", Toast.LENGTH_LONG).show()

        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
            p0!!.execSQL("Drop table IF EXISTS $dbTableAparelho")
            p0.execSQL(sqlCreateTable);
            p0.execSQL(sqlCreateTable2);
        }

    }

    fun LoadQuery(): ArrayList<Cliente> {
        var listCliente = ArrayList<Cliente>()
        val projections = arrayOf("ID", "nome", "cpf", "endereco", "telefone", "email")
        val cursor = sqlDB?.rawQuery("select * from Cliente order by  idServidor desc ", null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone = cursor.getString(cursor.getColumnIndex("telefone"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listCliente.add(
                        Cliente(
                            ID,
                            nome,
                            cpf,
                            endereco,
                            telefone,
                            email,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listCliente;


    }

    fun LoadQueryAparelho(): ArrayList<Aparelho> {
        var listAparelho = ArrayList<Aparelho>()

        val cursor = sqlDB?.rawQuery("select * from Aparelho  ", null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                var pronto = "";
                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial = cursor.getString(cursor.getColumnIndex("serial"))
                    pronto = "pronto";
                    val idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado =
                        "autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia =
                        "NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue =
                        "NAO_ENTREGUE"//cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs = "OBS"//cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada =
                        "dataEntrada"//cursor.getString(cursor.getColumnIndex("dataEntrada"))
                     val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                      val valor=cursor.getDouble(cursor.getColumnIndex("valor"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    val devolucao = cursor.getString(cursor.getColumnIndex("devolucao"))
                    listAparelho.add(
                        Aparelho(
                            ID,
                            nome,
                            modelo,
                            serial,
                            pronto,
                            idCliente,
                            autorizado,
                            garantia,
                            entregue,
                            defeito_obs,
                            dataEntrada,
                            dataSaida,
                            valor ,
                            idServidor,
                            devolucao
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listAparelho;


    }

    fun LoadQueryAparelhoByOS(ID: Long): ArrayList<Aparelho> {
        var listAparelho = ArrayList<Aparelho>()

        val cursor = sqlDB?.rawQuery("select * from Aparelho  where  idCliente = " + ID + " ;", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                var pronto = "";
                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial = cursor.getString(cursor.getColumnIndex("serial"))
                    pronto = "pronto";
                    val idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado =
                        "autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia =
                        "NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue =
                        cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs = cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada = cursor.getString(cursor.getColumnIndex("dataEntrada"))
                    val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                    val valor=cursor.getDouble(cursor.getColumnIndex("valor"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    val devolucao =  cursor.getString(cursor.getColumnIndex("devolucao"))
                    listAparelho.add(
                        Aparelho(
                            ID,
                            nome,
                            modelo,
                            serial,
                            pronto,
                            idCliente,
                            autorizado,
                            garantia,
                            entregue,
                            defeito_obs,
                            dataEntrada,
                            dataSaida,
                            valor,
                            idServidor,
                            devolucao
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listAparelho;


    }

    fun LoadQueryAparelhoByIdCliente(ID: Long): ArrayList<Aparelho> {
        var listAparelho = ArrayList<Aparelho>()

        val cursor =
            sqlDB?.rawQuery("select * from Aparelho  where  idServidor = " + ID + " ;", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                var pronto = "";
                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial = cursor.getString(cursor.getColumnIndex("serial"))
                    pronto = "pronto";
                    val idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado =
                        "autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia =
                        "NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue =
                        "NAO_ENTREGUE"//cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs = "OBS"//cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada =
                        "dataEntrada"//cursor.getString(cursor.getColumnIndex("dataEntrada"))
                    // val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                    //  val valor=cursor.getDouble(cursor.getColumnIndex("valor").toDouble().toInt())
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listAparelho.add(
                        Aparelho(
                            ID,
                            nome,
                            modelo,
                            serial,
                            pronto,
                            idCliente,
                            autorizado,
                            garantia,
                            entregue,
                            defeito_obs,
                            dataEntrada,
                            "",
                            8.0,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listAparelho;


    }

    fun Insert(values: ContentValues): Long {

        val ID = sqlDB!!.insert(dbTable, "", values)
        return ID
    }

    fun Query(
        projection: Array<String>,
        selection: String,
        selectionArgs: Array<String>,
        sorOrder: String
    ): Cursor {

        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
        return cursor
    }

    fun Delete(selection: String, selectionArgs: Array<String>): Int {

        val count = sqlDB!!.delete(dbTable, selection, selectionArgs)
        return count
    }

    fun Update(values: ContentValues, selection: String, selectionargs: Array<String>): Int {

        val count = sqlDB!!.update(dbTable, values, selection, selectionargs)
        return count
    }

    fun InsertAparelho(values: ContentValues): Long {

        val ID = sqlDB!!.insert(dbTableAparelho, "", values)
        sqlDB!!.close();
        return ID
    }

    fun LoadQueryClienteByID(idServidor: Long): ArrayList<Cliente> {
        var listCliente = ArrayList<Cliente>()
        val projections = arrayOf("ID", "nome", "cpf", "endereco", "telefone", "email")
        val cursor = sqlDB?.rawQuery("select * from Cliente order by  idServidor desc ", null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone = cursor.getString(cursor.getColumnIndex("telefone"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listCliente.add(
                        Cliente(
                            ID,
                            nome,
                            cpf,
                            endereco,
                            telefone,
                            email,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listCliente;


    }

    fun LoadQueryClienteByIDServidorZero(idServidor: Long): ArrayList<Cliente> {
        var listCliente = ArrayList<Cliente>()
        val projections = arrayOf("ID", "nome", "cpf", "endereco", "telefone", "email")
        val cursor = sqlDB?.rawQuery("select * from Cliente where  idServidor  = 0 ", null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone = cursor.getString(cursor.getColumnIndex("telefone"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listCliente.add(
                        Cliente(
                            ID,
                            nome,
                            cpf,
                            endereco,
                            telefone,
                            email,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listCliente;


    }

    fun updateCliente(idServidor: Long, id: Long) {

        val count =
            sqlDB!!.execSQL("update Cliente set idServidor = " + idServidor + " where id = " + id);
        sqlDB?.close()
    }

    fun LoadQueryAparelhoByIDServidorZero(idServidor: Long): ArrayList<Aparelho> {
        var listAparelho = ArrayList<Aparelho>()

        val cursor = sqlDB?.rawQuery("select * from Aparelho where  idServidor  = 0 ", null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    var pronto = "";

                    val ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial = cursor.getString(cursor.getColumnIndex("serial"))
                    pronto = "pronto";
                    val idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado =
                        "autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia =
                        "NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue =
                        "NAO_ENTREGUE"//cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs =
                        "OBS"//cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada =
                        "dataEntrada"//cursor.getString(cursor.getColumnIndex("dataEntrada"))
                    // val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                    //  val valor=cursor.getDouble(cursor.getColumnIndex("valor").toDouble().toInt())
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listAparelho.add(
                        Aparelho(
                            ID,
                            nome,
                            modelo,
                            serial,
                            pronto,
                            idCliente,
                            autorizado,
                            garantia,
                            entregue,
                            defeito_obs,
                            dataEntrada,
                            "",
                            8.0,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }


        cursor!!.close()
        return listAparelho;
    }

    fun updateAparelho(idServidor: Long, id: Long) {

        val count =
            sqlDB!!.execSQL("update Aparelho set idServidor = " + idServidor + " where id = " + id);
        sqlDB?.close()
    }

    fun selectAparelhoByCliente(id: Long): List<Aparelho> {
        var list = ArrayList<Aparelho>()
        val cursor = sqlDB!!.rawQuery(
            "select * from Aparelho where idCliente = " + 0 + " " +
                    "and id = " + id, null
        );
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    var pronto = "";

                    val ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial = cursor.getString(cursor.getColumnIndex("serial"))
                    pronto = "pronto";
                    val idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado =
                        "autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia =
                        "NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue =
                        "NAO_ENTREGUE"//cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs =
                        "OBS"//cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada =
                        "dataEntrada"//cursor.getString(cursor.getColumnIndex("dataEntrada"))
                    // val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                    //  val valor=cursor.getDouble(cursor.getColumnIndex("valor").toDouble().toInt())
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    list.add(
                        Aparelho(
                            ID,
                            nome,
                            modelo,
                            serial,
                            pronto,
                            idCliente,
                            autorizado,
                            garantia,
                            entregue,
                            defeito_obs,
                            dataEntrada,
                            "",
                            8.0,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }


        cursor!!.close()

        return list;
    }


    fun updateAparelhoAllFields(aparelho: Aparelho): Int {

        val count = sqlDB!!.update(dbTableAparelho, toValues(aparelho), "id=?", toArgs(aparelho))

//        val count = sqlDB!!.execSQL(
//            "update Aparelho set nome = " +"'" +aparelho.nome+"'" +
//                    ",  modelo = "+"'" + aparelho.modelo+"'" +
//                    ",  serial = " +"'" +aparelho.serial +"'"+
//                    ",  valor  = " + aparelho.valor +
//                    "  where id = " + aparelho.id
//        );
        println("---------------------------------- : " + count)
        // println(count.toString()+" -----")

        // sqlDB?.close()
        return count
    }

    fun toValues(aparelho: Aparelho): ContentValues {
        var values = ContentValues();
        values.put("nome", aparelho.nome);
        values.put("modelo", aparelho.modelo)
        values.put("serial", aparelho.serial)
        values.put("valor", aparelho.valor)
        values.put("devolucao", aparelho.devolucao)
        values.put("entregue", aparelho.entregue)
        values.put("dataSaida", aparelho.dataSaida)
        return values;
    }

    fun toValuesCliente(cliente: Cliente): ContentValues {
        var values = ContentValues();
        values.put("nome", cliente.nome);
        values.put("cpf", cliente.cpf)
        values.put("endereco", cliente.endereco)
        values.put("telefone", cliente.telefone)
        values.put("email", cliente.email)
        return values;
    }

    private fun toArgs(aparelho: Aparelho): Array<String>? {
        return arrayOf(aparelho.id.toString())
    }

    private fun toArgs(cliente: Cliente): Array<String>? {
        return arrayOf(cliente.id.toString())
    }

    fun updateClienteAllFields(cliente: Cliente): Int {

        val count = sqlDB!!.update(dbTable, toValuesCliente(cliente), "id=?", toArgs(cliente))

//        val count = sqlDB!!.execSQL(
//            "update Aparelho set nome = " +"'" +aparelho.nome+"'" +
//                    ",  modelo = "+"'" + aparelho.modelo+"'" +
//                    ",  serial = " +"'" +aparelho.serial +"'"+
//                    ",  valor  = " + aparelho.valor +
//                    "  where id = " + aparelho.id
//        );
        println("---------------------------------- : " + count)
        // println(count.toString()+" -----")

        // sqlDB?.close()
        return count
    }

    fun LoadQueryClienteByIDLocal(id: Long): ArrayList<Cliente> {
        var listCliente = ArrayList<Cliente>()

        val cursor = sqlDB?.rawQuery("select * from Cliente where  id =  " + id, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone = cursor.getString(cursor.getColumnIndex("telefone"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listCliente.add(
                        Cliente(
                            ID,
                            nome,
                            cpf,
                            endereco,
                            telefone,
                            email,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listCliente;


    }

    fun LoadQueryClienteByIDServidor(idServidor: Long): ArrayList<Cliente> {
        var listCliente = ArrayList<Cliente>()
        val projections = arrayOf("ID", "nome", "cpf", "endereco", "telefone", "email")
        val cursor = sqlDB?.rawQuery("select * from Cliente where idServidor = " + idServidor, null)

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val cpf = cursor.getString(cursor.getColumnIndex("cpf"))
                    val endereco = cursor.getString(cursor.getColumnIndex("endereco"))
                    val telefone = cursor.getString(cursor.getColumnIndex("telefone"))
                    val email = cursor.getString(cursor.getColumnIndex("email"))
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    listCliente.add(
                        Cliente(
                            ID,
                            nome,
                            cpf,
                            endereco,
                            telefone,
                            email,
                            idServidor
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listCliente;


    }

    fun LoadQueryAparelhoByIdCliente1(ID: Long): ArrayList<Aparelho> {
        var listAparelho = ArrayList<Aparelho>()

        val cursor =
            sqlDB?.rawQuery("select * from Aparelho  where  id = " + ID + " ;", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                var pronto = "";
                do {
                    val ID = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val modelo = cursor.getString(cursor.getColumnIndex("modelo"))
                    val serial = cursor.getString(cursor.getColumnIndex("serial"))
                    pronto = "pronto";
                    val idCliente = cursor.getInt(cursor.getColumnIndex("idCliente"))
                    val autorizado =
                        "autorizado"//cursor.getString(cursor.getColumnIndex("autorizado"))
                    val garantia =
                        "NAO_GARANTIA"//cursor.getString(cursor.getColumnIndex("garantia"))
                    val entregue =
                        cursor.getString(cursor.getColumnIndex("entregue"))
                    val defeito_obs = cursor.getString(cursor.getColumnIndex("defeito_obs"))
                    val dataEntrada =
                        cursor.getString(cursor.getColumnIndex("dataEntrada"))
                     val dataSaida=cursor.getString(cursor.getColumnIndex("dataSaida"))
                      val valor=cursor.getDouble(cursor.getColumnIndex("valor").toDouble().toInt())
                    val idServidor = cursor.getInt(cursor.getColumnIndex("idServidor"))
                    val devolucao = cursor.getString(cursor.getColumnIndex("devolucao"))
                    listAparelho.add(
                        Aparelho(
                            ID,
                            nome,
                            modelo,
                            serial,
                            pronto,
                            idCliente,
                            autorizado,
                            garantia,
                            entregue,
                            defeito_obs,
                            dataEntrada, dataSaida,
                            valor,
                            idServidor,
                            devolucao
                        )
                    )

                } while (cursor.moveToNext())
            }
        }
        cursor!!.close()
        return listAparelho;


    }
}


