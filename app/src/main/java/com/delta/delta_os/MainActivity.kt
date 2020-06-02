package com.delta.delta_os

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Cliente
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import com.delta.delta_os.util.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticcket.*
import kotlinx.android.synthetic.main.ticcket.view.*
import kotlinx.android.synthetic.main.ticcket.view.ivEdit
import retrofit2.Response

import retrofit2.Call
import retrofit2.Callback
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var listCliente = ArrayList<Cliente>();
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Session.context = this




        LoadQuery()
        println("PASSEI AQUI")
        val call = RetrofitInitializer().noteService().getClientes()
        call.enqueue(object : Callback<List<Cliente>?> {
            override fun onResponse(
                call: Call<List<Cliente>?>?,
                response: Response<List<Cliente>?>?
            ) {
                response?.body()?.let {
                    val notes: List<Cliente> = it
                    //configureList(notes)
                    notes.forEach {

                        var dbManager = DbManager(Session.context)
                        var values = ContentValues()

                        values.put("nome", it.nome)
                        values.put("idServidor", it.id)
                        values.put("cpf", "cpf")
                        values.put("endereco", "endereco")
                        values.put("telefone", "telefone")
                        values.put("email", "email")
                        var idc = it.id!!.toLong();
                        var listOfCliente = dbManager.LoadQueryClienteByID(idc)
                        if (listOfCliente != null) {
                            if (listOfCliente.size < 1) {
                                val ID = dbManager.Insert(values)
                            } else {

                            }
                        }
                    }

                    println(" Size aqui ->  " + notes.size)
                }
            }

            override fun onFailure(call: Call<List<Cliente>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })

        val callAparelho = RetrofitInitializer().noteService().getAparelho()
        callAparelho.enqueue(object : Callback<List<Aparelho>?> {
            override fun onResponse(
                call: Call<List<Aparelho>?>?,
                response: Response<List<Aparelho>?>?
            ) {
                response?.body()?.let {
                    val notes: List<Aparelho> = it
                    //configureList(notes)
                    notes.forEach {

                        var dbManager = DbManager(Session.context)
                        var values = ContentValues()

                        if (it.nome === null) {
                            values.put("nome", "semNome")
                        } else {
                            values.put("nome", it.nome)
                        }
                        if (it.modelo === null) {
                            values.put("modelo", "semModelo")
                        } else {
                            values.put("modelo", it.modelo)
                        }
                        if (it.serial === null) {
                            values.put("serial", "semserial")
                        } else {
                            values.put("serial", it.serial)
                        }
                        if (it.valor === null) {
                            values.put("valor", 0.0);
                        } else {
                            values.put("valor", it.valor);
                        }
                        if (it.id === null) {
                            values.put("idServidor", 0);
                        } else {
                            values.put("idServidor", it.idCliente);
                        }
                        if (it.pronto === null) {
                            values.put("pronto", "pronto")
                        } else {
                            values.put("pronto", it.pronto)
                        }
                        var ida = it.id!!.toLong()
                        var dbManagerSelect = DbManager(Session.context)


                        var listOfAparelho: List<Aparelho> =
                            dbManagerSelect.LoadQueryAparelhoByIdCliente(it.idCliente!!.toLong())
                        if (listOfAparelho.size < 1) {
                            val ID = dbManager.InsertAparelho(values)
                            println("menor que um " + listOfAparelho.size)

                            println("Aparelho : " + it.nome);
                            println("Aparelho id : " + ida);
                            println("Aparelho idServidor : " + it.idCliente);
                            println("Valor importante: -----")
                            println("valor = " + listOfAparelho.size)
                        } else {

                        }
                    }

                    //println(" Size aqui ->  "+notes.size)
                }
            }

            override fun onFailure(call: Call<List<Aparelho>?>?, t: Throwable?) {
                Log.e("onFailure error", t?.message)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            when (item.itemId) {
                R.id.addNote -> {
                    //Got to add paage
                    var intent = Intent(this, ClienteEditaActivity::class.java)
                    startActivity(intent)
                }
            }
            when (item.itemId) {
                R.id.addRefresh -> {
                    //Got to add paage
                    var dbManager = DbManager(this)
                    var list = dbManager.LoadQueryClienteByIDServidorZero(0)
                    val call = RetrofitInitializer().noteService().addCliente(list).enqueue(
                        object : Callback<List<Cliente>?> {
                            override fun onResponse(
                                call: Call<List<Cliente>?>?,
                                response: Response<List<Cliente>?>?
                            ) {
                                response?.body()?.let {
                                    val listClienteSync: List<Cliente> = it;
                                    
                                    listClienteSync.forEach{
                                        var dbManagerSelect = DbManager(Session.context)

                                        dbManagerSelect.updateCliente(it.id!!.toLong(),list[0].id!!.toLong())
                                        Toast.makeText(
                                            Session.context,
                                            " id Servidor = " + it.id+" id = "+list[0].id,
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }

                                }
                            }

                            override fun onFailure(call: Call<List<Cliente>?>?, t: Throwable?) {
                                Log.e("onFailure error", t?.message)
                            }
                        }
                    )
                    LoadQuery();
                }
            }
            println("FIM ..")
        }

        return super.onOptionsItemSelected(item)
    }


    fun LoadQuery() {

        var dbManager = DbManager(this)
        listCliente = dbManager.LoadQuery()
        var myNotesAdapter = MyClienteAdapter(this, listCliente)
        lvClientes.adapter = myNotesAdapter


    }

    inner class MyClienteAdapter : BaseAdapter {
        var listClienteAdapter = ArrayList<Cliente>();
        var context: Context? = null

        constructor (context: Context, listClienteAdapter: ArrayList<Cliente>) : super() {
            this.listClienteAdapter = listClienteAdapter;
            this.context = context

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticcket, null)
            var myVCliente = listClienteAdapter[position]
            myView.tvTitle.text = myVCliente.nome;
            myView.tvDes.text = myVCliente.idServidor.toString()

            myView.ivEdit.setOnClickListener(View.OnClickListener {
                Session.Companion.idCliente = myVCliente.idServidor?.toLong() ?: 1;
                var intent = Intent(this.context, MainAparelhoActivity::class.java)

                startActivity(intent)
                Toast.makeText(
                    this.context,
                    " edit! = " + Session.Companion.idCliente,
                    Toast.LENGTH_LONG
                ).show()
            });

            myView.ivDelete.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    this.context,
                    " Cliente deletado! ID = " + myVCliente.nome + " == " + myVCliente.id,
                    Toast.LENGTH_LONG
                ).show()

                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myVCliente.id.toString())
                dbManager.Delete("ID=?", selectionArgs)
                LoadQuery();
            })
            return myView
        }

        override fun getItem(position: Int): Any {
            return listClienteAdapter[position];
        }

        override fun getItemId(position: Int): Long {
            return position.toLong();
        }

        override fun getCount(): Int {
            return listClienteAdapter.size;

        }

    }

    fun GoToUpdate(cliente: Cliente) {
        var intent = Intent(this, ClienteEditaActivity::class.java)
//        intent.putExtra("nome",cliente.nome)
//        intent.putExtra("cpf",cliente.cpf)
//        intent.putExtra("endereco",cliente.endereco)
//        intent.putExtra("telefone",cliente.telefone)
//        intent.putExtra("email",cliente.email)
        startActivity(intent)
    }

    fun ConvertStreamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var AllString: String = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    AllString += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {
        }



        return AllString
    }

}
