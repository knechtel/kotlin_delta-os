package com.delta.delta_os.editCliente

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.delta.delta_os.R
import com.delta.delta_os.bean.Session
import kotlinx.android.synthetic.main.activity_id_cliente.*

class IdClienteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_cliente)

        sendButton.setOnClickListener({
            Session.idLocal = idText.getText().toString().toInt()
            var intent = Intent(this,ClienteActivityEdit::class.java)
            startActivity(intent)
        })
    }
}
