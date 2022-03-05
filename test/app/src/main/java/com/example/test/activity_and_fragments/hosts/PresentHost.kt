package com.example.test.activity_and_fragments.hosts

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity_and_fragments.MainActivity
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO

class PresentHost : AppCompatActivity() {

    private val mCharacterVM: CharacterDAO by viewModels()
    private val mGameVM: GameDAO by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_present_game)

        val gameId = getSharedPreferences("id", 0).getString("PresentGame", "0")!!.toInt()
        mCharacterVM.initGameId(gameId)
        mCharacterVM.loadCharactersByGameId(gameId)
        mGameVM.initGameName(gameId)
    }
    fun backToMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}