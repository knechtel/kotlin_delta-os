package com.delta.delta_os

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import com.delta.delta_os.util.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_cadastro_aparelho.*



class CadastroAparelhoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_aparelho)
        this.setTitle("Cadastro de Aparelho");

        salvarButton.setOnClickListener{
            var dbManager= DbManager(this)
            var values= ContentValues()

            values.put("nome",equipamentoText.text.toString())
            values.put("modelo",modeloText.text.toString())

            values.put("serial",serialText.text.toString())
            values.put("valor",valorText.text.toString().toDouble());
            values.put("idCliente",Session.idLocalCadAparelho);
            values.put("pronto","NAO_PRONTO")
            values.put("idServidor",0)
            var ID = dbManager.InsertAparelho(values);
            Toast.makeText(this, "Aparelho inserido com sucesso!", Toast.LENGTH_LONG).show()




            var intent = Intent(this,MainAparelhoActivity::class.java)
            intent.putExtra("idCliente",ID)
            startActivity(intent)
        }


    }
}
