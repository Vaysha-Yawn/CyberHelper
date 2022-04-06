package com.example.test.activity_and_fragments.hosts

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.activity_and_fragments.MainActivity
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.SkillTestVM

class PresentHost : AppCompatActivity() {

    private val mSkillVM: SkillTestVM by viewModels()
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

    fun backToMain() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    fun openFight(id: Int) {
        val i = Intent(this, FightHost::class.java)
        i.putExtra("characterId", id)
        i.putExtra("fragment", "fight")
        startActivity(i)
        finish()
    }
    fun openIniciativa(id: Int) {
        val i = Intent(this, FightHost::class.java)
        i.putExtra("characterId", id)
        i.putExtra("fragment", "iniciativa")
        startActivity(i)
        finish()
    }
    fun openSkillTest(id: Int) {
        val i = Intent(this, FightHost::class.java)
        i.putExtra("characterId", id)
        i.putExtra("fragment", "skillTest")
        startActivity(i)
        finish()
    }
}