package com.example.test.activity_and_fragments.hosts

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.activity_and_fragments.MainActivity
import com.example.test.viewModels.GameSystemDAO
import com.example.test.viewModels.SystemSettingsVM

class SettingsHost : AppCompatActivity() {

    private val mGameSystemDAO: GameSystemDAO by viewModels()
    private val systemSettingsVM: SystemSettingsVM by viewModels()

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