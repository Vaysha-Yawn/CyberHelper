package com.example.test.activity_and_fragments.setting

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity_and_fragments.MainActivity
import com.example.test.viewModels.GameDAO

class SettingsHost : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_settings)

    }
    fun backToMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}