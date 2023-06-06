package com.you.cashfor

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.you.cashfor.databinding.ActivityRegBinding

class RegActivity : AppCompatActivity() {

    private lateinit var bindiing: ActivityRegBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        if(getSharedPreferences("prefs",Context.MODE_PRIVATE).getBoolean("reg",false)) {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        super.onCreate(savedInstanceState)
        bindiing = ActivityRegBinding.inflate(layoutInflater)
        setContentView(bindiing.root)
        bindiing.button.setOnClickListener {
            if(bindiing.input1.text?.isEmpty() == true) {
                bindiing.input1.error = "Введите имя"
            } else if(bindiing.input2.text?.isEmpty() == true) {
                bindiing.input2.error = "Введите почту"
            } else if(bindiing.input3.text?.isEmpty() == true) {
                bindiing.input3.error = "Введите пароль"
            } else {
                getSharedPreferences("prefs",Context.MODE_PRIVATE).edit().putBoolean("reg",true).apply()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        bindiing.input1.addTextChangedListener {
            bindiing.input1.error = null
        }
        bindiing.input2.addTextChangedListener {
            bindiing.input2.error = null
        }
        bindiing.input3.addTextChangedListener {
            bindiing.input3.error = null
        }

    }
}