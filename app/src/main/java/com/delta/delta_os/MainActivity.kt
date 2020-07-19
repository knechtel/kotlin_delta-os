package com.delta.delta_os

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Cliente
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import com.delta.delta_os.dto.AparelhoDto
import com.delta.delta_os.editAparelho.EditAparelhoActivity
import com.delta.delta_os.editCliente.IdClienteActivity
import com.delta.delta_os.modal.MainDialogActivity
import com.delta.delta_os.service.AparelhoService
import com.delta.delta_os.service.AparelhoServiceSync
import com.delta.delta_os.util.RetrofitInitializer
import com.delta.delta_os.util.Util
import kotlinx.android.synthetic.main.activity_edit_aparelho.*
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
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Session.context = this

        LoadQuery()

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
                        values.put("cpf", it.cpf)
                        values.put("endereco", it.endereco)
                        values.put("telefone", it.telefone)
                        values.put("email", it.email)
                        var idc = it.id!!.toLong();
                        var listOfCliente = dbManager.LoadQueryClienteByIDServidor(idc)
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

        val callAparelho = RetrofitInitializer().noteService().getAparelho().apply {
            enqueue(object : Callback<List<AparelhoDto>?> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(
                    call: Call<List<AparelhoDto>?>?,
                    response: Response<List<AparelhoDto>?>?
                ) {
                    response?.body()?.let {
                        val notes: List<AparelhoDto> = it

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
                                values.put("idServidor", it.id);
                            }
                            if (it.pronto === null) {
                                values.put("pronto", "pronto")
                            } else {
                                values.put("pronto", it.pronto)
                            }
                            if (it.dataEntrada == null) {
                                values.put("dataEntrada", "null")
                            } else {
                                values.put("dataEntrada",it.dataEntrada!!)
                            }
                            values.put("devolucao", it.devolucao)
                            values.put("entregue", it.entregue)
                            values.put("defeito_obs",it.defeito_obs)
                            if (it.dataSaida != null)
                                values.put("dataSaida",it.dataSaida!!)
                            else
                                values.put("dataSaida","dataSaida")
                            var ida = it.id!!.toLong()
                            var dbManagerSelect = DbManager(Session.context)


                            var listOfAparelho: List<Aparelho> =
                                dbManagerSelect.LoadQueryAparelhoByIdCliente1(it.id!!.toLong())
                            if (listOfAparelho.size < 1) {
                                values.put("idCliente", it.id)
                                val ID = dbManager.InsertAparelho(values)

                            } else if (listOfAparelho.size == 1) {
                                println()

                                var db = DbManager(Session.context)

                                var listAparelho = db.LoadQueryAparelhoByIdCliente(it.id!!.toLong())
                                var listAparelhoDto=ArrayList<AparelhoDto>()
                                println("passei aqui 1234556778")
                                println(listAparelho.size)
                               listAparelho.forEach(
                                   {
                                       listAparelhoDto.add(AparelhoDto().build(it))
                                   }
                               )


                                val callAparelho = RetrofitInitializer().aparelhoService().
                                mergeAparelho(listAparelhoDto).enqueue(
                                    object : Callback<List<AparelhoDto>?> {
                                        override fun onResponse(
                                            call: Call<List<AparelhoDto>?>?,
                                            response: Response<List<AparelhoDto>?>?
                                        ) {
                                        }

                                        override fun onFailure(
                                            call: Call<List<AparelhoDto>?>?,
                                            t: Throwable?
                                        ) {
                                            Log.e("onFailure error", t?.message)
                                        }
                                    }
                                )

                                println("muita atençãp aqui ...")
                            }


                        }
                    }
                }

                override fun onFailure(call: Call<List<AparelhoDto>?>?, t: Throwable?) {
                    Log.e("onFailure error", t?.message)
                }
            })
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                    println("Ola meu teste ---->1993")
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

                                    listClienteSync.forEach {

                                        list.forEach { clienteAux ->
                                            var dbManagerSelect = DbManager(Session.context)
                                            dbManagerSelect.updateCliente(
                                                it.id!!.toLong(),
                                                clienteAux.id!!.toLong()
                                            )

                                        }

                                        Toast.makeText(
                                            Session.context,
                                            "id Servidor = " + it.id + " id = " + list[0].id,
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
                    println("APARELHO ------ ----- ------ ---- ---- ")
                    println("CHMA AQUI")
                    var dbManagerAparelho = DbManager(Session.context)
                    var listAparelho: List<Aparelho>
                    var i = AparelhoServiceSync().atualizaAparelho()

                }
            }


            //     println("valor de AparelhoServiceSync 12345 =  =  "+i.id.toString()+" nome"+i.nome)
            println("FIM ..")
            when (item.itemId) {
                R.id.edit_cliente -> {

                    var intent = Intent(this, IdClienteActivity::class.java)
                    startActivity(intent)
                }
            }

            when(item.itemId){
                R.id.app_bar_search ->{
                    println("go sincronismo")
                }
            }
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
            myView.tvDes.text =
                "id servidor: " + myVCliente.idServidor.toString() + "\nid local: " + myVCliente.id




            myView.ivEdit.setOnClickListener(View.OnClickListener {
                Session.Companion.idCliente = myVCliente.id?.toLong() ?: 199;
                var dbManager= DbManager(Session.context)
                var intent = Intent(this.context, MainAparelhoActivity::class.java)
                // aqui tem um bug
                Session.Companion.idLocalCadAparelho = myVCliente.id?.toInt() ?: 199;
                Session.idLocalAparelhos = myVCliente.id?.toInt() ?: -1;
                Session.uuidCliente =
                    dbManager.LoadQueryClienteByIDLocal(myVCliente!!.id!!.toLong()).get(0).uuidCliente.toString()
                startActivity(intent)
                Toast.makeText(
                    this.context,
                    " edit! = " + Session.Companion.idLocalAparelhos,
                    Toast.LENGTH_LONG
                ).show()
            });

            myView.ivDelete.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    this.context,
                    " Cliente deletado! ID = " + myVCliente.nome + " == " + myVCliente.id,
                    Toast.LENGTH_LONG
                ).show()

//                var dbManager = DbManager(this.context!!)
//                val selectionArgs = arrayOf(myVCliente.id.toString())
//                dbManager.Delete("ID=?", selectionArgs)
                var intent = Intent(this.context, MainDialogActivity::class.java).also {
                    startActivity(it)
                }

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

}
