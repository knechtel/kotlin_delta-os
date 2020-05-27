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
import com.delta.delta_os.bean.Aparelho
import kotlinx.android.synthetic.main.activity_main_aparelho.*
import kotlinx.android.synthetic.main.ticcket.view.*
import java.util.Date

class MainAparelhoActivity : AppCompatActivity() {
    var listAparelho = ArrayList<Aparelho>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_aparelho)
        listAparelho.add(
            Aparelho(1,"TVLCD","modelo","serial","pronto",3,
            "autorizado","NAO_GARANTIA","NAO_ENTREGUE","defeito",
                 java.sql.Date( Date().getTime()), java.sql.Date( Date().getTime()),120.0

            )

        )
        println("print lista ++++++++++++")
        println(listAparelho.size)
        var myNotesAdapter = MyAparelhoAdapter(this, listAparelho)
        lvAparelhos.adapter = myNotesAdapter
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

    inner class MyAparelhoAdapter : BaseAdapter {
        var listAparelhoAdapter = ArrayList<Aparelho>();
        var context: Context? = null

        constructor(context: Context, lisAparelhoAdapter: ArrayList<Aparelho>) : super() {
            this.context = context;
            this.listAparelhoAdapter = lisAparelhoAdapter;
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.ticcket, null)
            var myVCliente = listAparelhoAdapter[position]
            myView.tvTitle.text = myVCliente.nome;
            myView.tvDes.text = myVCliente.autorizado

            return myView;
        }

        override fun getItem(position: Int): Any {
            return listAparelhoAdapter[position];
        }

        override fun getItemId(position: Int): Long {
           return position.toLong();

        }

        override fun getCount(): Int {
            return listAparelhoAdapter.size
        }

    }
}
