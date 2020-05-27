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
import com.hussein.startup.DbManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticcket.view.*
import kotlinx.android.synthetic.main.ticcket.view.ivEdit

class MainActivity : AppCompatActivity() {

    var listCliente = ArrayList<Cliente>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listCliente.add(
            Cliente(
                1, "Maiquel Knechtel123",
                "016.379.480-42", "coca barcelos", "maiquelknechtel@gmail.com", "51-36612082"
            )
        )
        listCliente.add(
            Cliente(
                2, "Micheli Knechtel",
                "016.379.480-42", "coca barcelos", "maiquelknechtel@gmail.com", "51-36612082"
            )
        )
        LoadQuery()


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
            myView.tvDes.text = myVCliente.cpf;

            myView.ivEdit.setOnClickListener(View.OnClickListener {
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
