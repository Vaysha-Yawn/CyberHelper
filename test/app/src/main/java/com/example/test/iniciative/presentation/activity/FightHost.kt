package com.example.test.iniciative.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.test.R
import com.example.test.present_game.presentation.activity.PresentHost
import com.example.test.viewModels.CharacterDAO
import com.example.test.iniciative.presentation.view_model.FewRollVM
import com.example.test.viewModels.GameDAO
import com.example.test.viewModels.SkillTestVM

class FightHost : AppCompatActivity() {

    private val mGameVM: GameDAO by viewModels()
    private val mSkillVM: SkillTestVM by viewModels()
    private val mCharacterVM: CharacterDAO by viewModels()
    private val VM: FewRollVM by viewModels()
    private var characterId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_fight)
        /*val loadFragment = intent.getStringExtra("fragment")?:""
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigate(R.id.Initiative)
*/
        characterId = intent.getIntExtra("characterId", 0)
        val gameId = getSharedPreferences("id", 0).getString("PresentGame", "0")!!.toInt()
        mCharacterVM.initGameId(gameId)
        if (mCharacterVM.characterList.value.isNullOrEmpty()) {
            mCharacterVM.loadCharactersByGameId(gameId)
        }
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
