package com.delta.delta_os

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cliente_edita.*
import java.lang.Exception

class ClienteEditaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_edita)
        var bundle:Bundle= intent.extras!!
        try{
            nomeText.setText(
                bundle.getString("nome").toString()
            )
            cpfText.setText(
                bundle.getString("cpf").toString()
            )


        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }
}
