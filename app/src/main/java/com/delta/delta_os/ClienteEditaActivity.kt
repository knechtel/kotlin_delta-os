package com.delta.delta_os

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import kotlinx.android.synthetic.main.activity_cliente_edita.*
import java.util.UUID;


class ClienteEditaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_edita)
        this.setTitle("Cadastro de Cliente");
        salvarButton.setOnClickListener {
            var dbManager= DbManager(this)
            var values = ContentValues().also {

                it.put("nome",nomeText.text.toString())
                it.put("cpf",cpfText.text.toString())
                it.put("endereco",enderecoText.text.toString())
                it.put("telefone",telefoneText.text.toString())
                it.put("email",emailText.text.toString())
                it.put("idServidor",0);
                it.put("uuidCliente",UUID.randomUUID().toString())
            };
            val ID = dbManager.Insert(values)

            Toast.makeText(this, " Cliente adicionado! ID ="+ID, Toast.LENGTH_LONG).show()

            Session.Companion.idLocalCadAparelho = ID.toInt();
            Session.idLocalAparelhos =ID.toInt();
            var intent=  Intent(this,MainAparelhoActivity::class.java)
           // intent.putExtra("idCliente",ID);
            startActivity(intent)
        }
    }
}
