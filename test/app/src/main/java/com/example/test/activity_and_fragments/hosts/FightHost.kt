package com.example.test.activity_and_fragments.hosts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.leanback.app.BrowseSupportFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.test.R
import com.example.test.viewModels.CharacterDAO
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.SkillTestVM

class FightHost : AppCompatActivity() {

    private val mGameVM: GameDAO by viewModels()
    private val mSkillVM: SkillTestVM by viewModels()
    private val mCharacterVM: CharacterDAO by viewModels()
    private var characterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_fight)
        val loadFragment = intent.getStringExtra("fragment")?:""
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigate(R.id.iniciativa)

        characterId = intent.getIntExtra("characterId", 0)
        val gameId = getSharedPreferences("id", 0).getString("PresentGame", "0")!!.toInt()
        mCharacterVM.initGameId(gameId)
        mCharacterVM.loadCharactersByGameId(gameId)
        mGameVM.initGameName(gameId)
        mCharacterVM.characterId = characterId
    }
    
    fun backToCharacterMenu() {
        val i = Intent(this, PresentHost::class.java)
        i.putExtra("fragment", "CharacterMenu")
        i.putExtra("characterId", characterId)
        startActivity(i)
        finish()
    }

    fun backToHome() {
        val i = Intent(this, PresentHost::class.java)
        startActivity(i)
        finish()
    }
}

// этот текст написан через Acode 