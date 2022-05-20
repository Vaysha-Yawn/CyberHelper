package com.example.test.activity_and_fragments

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity_and_fragments.about.About
import com.example.test.activity_and_fragments.hosts.LoadHost
import com.example.test.activity_and_fragments.hosts.NewHost
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.activity_and_fragments.setting.SettingsHost
import com.example.test.data_base.TemplateGameSystem
import com.example.test.viewModels.GameSystemDAO

class MainActivity : AppCompatActivity() {

    private val mGameSystemDAO: GameSystemDAO by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gameId = getSharedPreferences("id", 0).getString("PresentGame", "0")?.toInt()
        val load = findViewById<Button>(R.id.Main_Load)

        if (mGameSystemDAO.findGameSystemId("cyberPuckSystem") == null) {
            mGameSystemDAO.addGameSystem(TemplateGameSystem().cyberPuckSystem)
        }

        if (mGameSystemDAO.findGameSystemId("DnDSystem") == null) {
            mGameSystemDAO.addGameSystem(TemplateGameSystem().DnDSystem)
        }

        val vContinue = findViewById<Button>(R.id.Main_Continue)
        if (gameId != 0) {
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