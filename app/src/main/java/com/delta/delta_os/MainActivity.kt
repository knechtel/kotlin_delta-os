package com.delta.delta_os

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.delta.delta_os.bean.Cliente
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticcket.view.*
import kotlinx.android.synthetic.main.ticcket.view.ivEdit
import java.util.*

class MainActivity : AppCompatActivity() {

    var listCliente = ArrayList<Cliente>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      LoadQuery()
     //   var myNotesAdapter = MyClienteAdapter(this, listCliente)
          //   lvClientes.adapter = myNotesAdapter


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
                    LoadQuery();
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
            myView.tvDes.text = myVCliente.id.toString();

            myView.ivEdit.setOnClickListener(View.OnClickListener {
                Session.Companion.idCliente = myVCliente.id?.toLong() ?:1;
                var intent=  Intent(this.context,MainAparelhoActivity::class.java)

                startActivity(intent)
                Toast.makeText(this.context, " edit!", Toast.LENGTH_LONG).show()
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
}
