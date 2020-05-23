package com.delta.delta_os

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticcket.view.*

class MainActivity : AppCompatActivity() {

    var listCliente = ArrayList<Cliente>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listCliente.add(Cliente(1,"Maiquel Knechtel",
                "016.379.480-42","coca barcelos","maiquelknechtel@gmail.com","51-36612082"))
        listCliente.add(Cliente(1,"Micheli Knechtel",
            "016.379.480-42","coca barcelos","maiquelknechtel@gmail.com","51-36612082"))

        var myClientesAdap =MyClienteAdapter(listCliente)
        lvClientes.adapter=myClientesAdap;
    }

    inner class MyClienteAdapter:BaseAdapter{
        var listClienteAdapter = ArrayList<Cliente>();
    constructor (listClienteAdapter: ArrayList<Cliente>):super(){
        this.listClienteAdapter=listClienteAdapter;
    }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticcket,null)
            var myVCliente = listClienteAdapter[position]
            myView.tvTitle.text=myVCliente.nome;
            myView.tvDes.text=myVCliente.cpf;
            myView.ivEdit.setOnClickListener(View.OnClickListener {
                GoToUpdate(myVCliente);
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

    fun GoToUpdate(cliente:Cliente){
        var intent=  Intent(this,ClienteEditaActivity::class.java)
        intent.putExtra("nome",cliente.nome)
        intent.putExtra("cpf",cliente.cpf)
        startActivity(intent)
    }
}
