package com.example.test.settings.presentation.fragments.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.main.MainActivity
import com.example.test.settings.presentation.view_model.CreateSystemVM
import com.example.test.settings.presentation.view_model.SystemSettingsVM
import com.example.test.viewModels.GameSystemDAO

class SettingsHost : AppCompatActivity() {

    private val mGameSystemDAO: GameSystemDAO by viewModels()
    private val systemSettingsVM: SystemSettingsVM by viewModels()
    private val createSystemVM: CreateSystemVM by viewModels()

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