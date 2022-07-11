package com.example.test.new_game.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.present_game.presentation.activity.PresentHost
import com.example.test.main.MainActivity
import com.example.test.data_base.realm.character.CharacterDAO
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.data_base.realm.game_system.GameSystemDAO

class NewHost : AppCompatActivity() {

    private val mCharacterVM: CharacterDAO by viewModels()
    private val mGameVM: GameDAO by viewModels()
    private val mGameSystemVM: GameSystemDAO by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_new_game)

        val gameId = mGameVM.getNewGameId()
        getSharedPreferences("id", 0).edit().putString("newGameId", gameId.toString()).apply()
        mCharacterVM.initGameId(gameId)
        mCharacterVM.loadCharactersByGameId(gameId)
    }
    fun backToMain(){
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }
    fun goToPresentGame(){
        val i = Intent(this, PresentHost::class.java)
        startActivity(i)
        finish()
    }
}