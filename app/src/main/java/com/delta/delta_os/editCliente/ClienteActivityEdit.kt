package com.delta.delta_os.editCliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.delta.delta_os.MainActivity
import com.delta.delta_os.MainAparelhoActivity
import com.delta.delta_os.R
import com.delta.delta_os.bean.Cliente
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import kotlinx.android.synthetic.main.activity_cliente_edit.*
import kotlinx.android.synthetic.main.activity_cliente_edit.nomeText
import kotlinx.android.synthetic.main.activity_edit_aparelho.*


class ClienteActivityEdit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_edit)
        var db = DbManager(this)
        var list  = db.LoadQueryClienteByIDLocal(Session.idLocal.toLong())

        if(list.size==1) {
            nomeText.setText(list[0].nome)
            cpfText.setText(list[0].cpf)
            enderecoText.setText(list[0].endereco)
            telefoneText.setText(list[0].telefone);
            emailText.setText(list[0].email)
        }else{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Registro nao encontrado!", Toast.LENGTH_LONG).show()
        }

        button2.setOnClickListener({

            var cliente = Cliente()
            cliente.nome = nomeText.getText().toString()
            cliente.cpf = cpfText.getText().toString()
            cliente.endereco = enderecoText.getText().toString()
            cliente.telefone = telefoneText.getText().toString()
            cliente.email = emailText.getText().toString()
            cliente.id = list[0].id
            var dbManager = DbManager(this);
            dbManager.updateClienteAllFields(cliente)
            Toast.makeText(this, " Cliente editado com sucesso!", Toast.LENGTH_LONG).show()
            var intent=  Intent(this, MainActivity::class.java)

            startActivity(intent)
        })
    }
}
