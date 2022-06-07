package com.example.test.load.presentaion

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.main.MainActivity
import com.example.test.viewModels.GameDAO

class LoadHost : AppCompatActivity() {

    private val mGameVM: GameDAO by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_load)
        mGameVM.initAllGames()
    }
    fun backToMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}