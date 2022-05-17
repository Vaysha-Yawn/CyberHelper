package com.example.test.activity_and_fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.test.R
import com.example.test.activity_and_fragments.about.About
import com.example.test.activity_and_fragments.hosts.LoadHost
import com.example.test.activity_and_fragments.hosts.NewHost
import com.example.test.activity_and_fragments.hosts.PresentHost
import com.example.test.activity_and_fragments.setting.SettingsHost
import com.example.test.data_base.TemplateGameSystem
import com.example.test.databinding.DialogChooseAddModificationBinding
import com.example.test.viewModels.GameSystemDAO

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

            val settings = findViewById<Button>(R.id.Main_Settings)
            settings.setOnClickListener {
                val i = Intent(this, SettingsHost::class.java)
                startActivity(i)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_LONG).show()
        }
    }

}

class ChooseSystemDialogFragment : DialogFragment() {

    private val mGameSystemDAO: GameSystemDAO by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.dialog_choose_add_modification, container, false)

        val binding = DialogChooseAddModificationBinding.bind(view)
        fun bind() = with(binding) {
            title.text = "Выберите нужную игровую систему"
            variant1.text = "Киберпанк"
            variant2.text = "ДнД"
            variant1.setOnClickListener {
                mGameSystemDAO.addGameSystem(TemplateGameSystem().cyberPuckSystem)
                mGameSystemDAO.initGameSystemById(0)
                dismiss()
            }
            variant2.setOnClickListener {
                mGameSystemDAO.addGameSystem(TemplateGameSystem().DnDSystem)
                mGameSystemDAO.initGameSystemById(1)
                dismiss()
            }
        }
        bind()

        return view
    }

}