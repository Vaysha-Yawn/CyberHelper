package com.example.test.main

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.about.view.About
import com.example.test.load.presentaion.LoadHost
import com.example.test.new_game.presentation.activity.NewHost
import com.example.test.present_game.presentation.activity.PresentHost
import com.example.test.settings.presentation.fragments.activity.SettingsHost
import com.example.test.data_base.realm.game.GameDAO
import com.example.test.data_base.realm.game_system.GameSystemDAO

class MainActivity : AppCompatActivity() {

    private val mGameSystemDAO: GameSystemDAO by viewModels()
    private val mGameDAO: GameDAO by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mGameDAO.initAllGames()
        val haveGamesBoolean = mGameDAO.gameList.value?.isEmpty()

        val load = findViewById<Button>(R.id.Main_Load)

        /*if (mGameSystemDAO.findGameSystemId("cyberPunkSystem") == null) {
            mGameSystemDAO.addGameSystem(TemplateGameSystem().cyberPunkSystem)
            Log.d("aaaaa", "Система киберпанк создана")
        }

        if (mGameSystemDAO.findGameSystemId("DnDSystem") == null) {
            mGameSystemDAO.addGameSystem(TemplateGameSystem().DnDSystem)
            Log.d("aaaaa", "Система днд создана")
        }*/

        val vContinue = findViewById<Button>(R.id.Main_Continue)
        if (haveGamesBoolean == false) {
            load.setOnClickListener {
                val i = Intent(this, LoadHost::class.java)
                startActivity(i)
            }
            vContinue.setOnClickListener {
                val i = Intent(this, PresentHost::class.java)
                startActivity(i)
            }
        } else {
            load.visibility = GONE
            vContinue.visibility = GONE
        }

        val newGame = findViewById<Button>(R.id.Main_NewGame)
        newGame.setOnClickListener {
            val i = Intent(this, NewHost::class.java)
            startActivity(i)
        }

        val vAbout = findViewById<Button>(R.id.Main_About)
        vAbout.setOnClickListener {
            val i = Intent(this, About::class.java)
            startActivity(i)
        }

        val settings = findViewById<Button>(R.id.Main_Settings)
        settings.setOnClickListener {
            val i = Intent(this, SettingsHost::class.java)
            startActivity(i)
        }

    }

}