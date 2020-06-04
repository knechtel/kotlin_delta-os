package com.delta.delta_os.editAparelho

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.delta.delta_os.R
import com.delta.delta_os.bean.Aparelho
import com.delta.delta_os.bean.Session
import com.delta.delta_os.db.DbManager
import kotlinx.android.synthetic.main.activity_edit_aparelho.*


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
    }
}
