package com.delta.delta_os.editAparelho

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.delta.delta_os.R
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import kotlinx.android.synthetic.main.activity_cliente_edita.*
import kotlinx.android.synthetic.main.activity_edit_aparelho.*
import kotlinx.android.synthetic.main.activity_edit_aparelho.nomeText


class EditAparelhoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_aparelho)

      var dbManager = DbManager(this);
      var listAparelho =   dbManager.LoadQueryAparelhoByOS(Session.idLocal.toLong())

        nomeText.setText(listAparelho[0].nome)
        modeloText.setText(listAparelho[0].modelo)
        serialText.setText(listAparelho[0].serial)
        valorText.setText(listAparelho[0].valor.toString())


        button.setOnClickListener {
            var aparelho = Aparelho()
            aparelho.nome = nomeText.getText().toString()
            aparelho.modelo = modeloText.getText().toString()
            aparelho.serial = serialText.getText().toString()
            aparelho.valor =  valorText.getText().toString().toDouble()
            aparelho.id =listAparelho[0].id
            var dbManager = DbManager(this);
            dbManager.updateAparelhoAllFields(aparelho)
            Toast.makeText(this,"Aparelho atualizado com sucesso id = "+aparelho.id.toString(),
                Toast.LENGTH_SHORT).show();
        }
    }
}
