package com.example.test.activity_and_fragments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.Button
import android.widget.Toast
import com.example.test.R
import com.example.test.activity_and_fragments.about.About
import com.example.test.activity_and_fragments.hosts.LoadHost
import com.example.test.activity_and_fragments.hosts.NewHost
import com.example.test.activity_and_fragments.hosts.PresentHost
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
        val gameId = getSharedPreferences("id", 0).getString("PresentGame", "0")?.toInt()
        val load = findViewById<Button>(R.id.Main_Load)
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
        } catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        }
    }
}