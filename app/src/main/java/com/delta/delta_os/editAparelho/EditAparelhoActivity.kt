package com.delta.delta_os.editAparelho

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.delta.delta_os.R
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import com.delta.delta_os.util.Util
import kotlinx.android.synthetic.main.activity_cliente_edita.*
import kotlinx.android.synthetic.main.activity_edit_aparelho.*
import kotlinx.android.synthetic.main.activity_edit_aparelho.nomeText


class EditAparelhoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_aparelho)

      var dbManager = DbManager(this);
      var listAparelho =   dbManager.LoadQueryAparelhoByIdCliente1(Session.idLocalCadAparelho.toLong())

        nomeText.setText(listAparelho[0].nome)
        modeloText.setText(listAparelho[0].modelo)
        serialText.setText(listAparelho[0].serial)
        valorText.setText(listAparelho[0].valor.toString())
        dataEntradaText.setText("Data de Entrada - "+listAparelho[0].dataEntrada)
        defeitoObsText.setText(listAparelho[0].defeitoObs)
        if(listAparelho[0].devolucao.toString().equals("NAO")){
            checkBoxDevolucao.isChecked=false
        }else{
            checkBoxDevolucao.isChecked=true
        }
        if(listAparelho[0].entregue.toString().equals("ENTREGUE")){
            checkBoxEntregue.isChecked=true
        }else{
            checkBoxEntregue.isChecked=false
        }
        if(listAparelho[0].dataSaida!=null)
            dataSaidaText.setText("Data Saida - "+listAparelho[0].dataSaida)

        button.setOnClickListener {
            var aparelho = Aparelho()
            aparelho.nome = nomeText.getText().toString()
            aparelho.modelo = modeloText.getText().toString()
            aparelho.serial = serialText.getText().toString()
            aparelho.valor =  valorText.getText().toString().toDouble()
            aparelho.id =listAparelho[0].id
            if(checkBoxDevolucao.isChecked){
                aparelho.devolucao="SIM"
            }else {
                aparelho.devolucao="NAO"
            }
            if(checkBoxEntregue.isChecked){
                aparelho.entregue = "ENTREGUE"
                val data = Util().getCurrentDateTime()
                aparelho.dataSaida = Util().toSimpleString(data)

            }else{
                aparelho.entregue = "NAO_ENTREGUE"
            }
            var dbManager = DbManager(this);
            dbManager.updateAparelhoAllFields(aparelho)
            Toast.makeText(this,"Aparelho atualizado com sucesso id = "+aparelho.id.toString(),
                Toast.LENGTH_SHORT).show();
        }
    }
}
