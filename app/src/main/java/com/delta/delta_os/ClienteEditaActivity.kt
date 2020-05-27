package com.delta.delta_os

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hussein.startup.DbManager
import kotlinx.android.synthetic.main.activity_cliente_edita.*


class ClienteEditaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_edita)
        salvarButton.setOnClickListener {
            var dbManager= DbManager(this)
            var values= ContentValues()

            values.put("nome",nomeText.text.toString())
            values.put("cpf",cpfText.text.toString())
            values.put("endereco",enderecoText.text.toString())
            values.put("telefone",telefoneText.text.toString())
            values.put("email",emailText.text.toString())

            val ID = dbManager.Insert(values)

            Toast.makeText(this, " Cliente adicionado! ID ="+ID, Toast.LENGTH_LONG).show()


            var intent=  Intent(this,MainAparelhoActivity::class.java)

            startActivity(intent)
        }
    }
}
