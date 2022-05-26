package com.example.test.activity_and_fragments.hosts

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.test.R
import com.example.test.activity_and_fragments.MainActivity
import com.example.test.viewModels.*

class PresentHost : AppCompatActivity() {

    private val mSkillVM: SkillTestVM by viewModels()
    private val mCharacterVM: CharacterDAO by viewModels()
    private val mGameVM: GameDAO by viewModels()
    private val mGameSystemVM: GameSystemDAO by viewModels()
    private val mInitiativeFightVM: InitiativeFightVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.host_present_game)

        val gameId = getSharedPreferences("id", 0).getString("PresentGame", "0")!!.toInt()
        mCharacterVM.initGameId(gameId)
        if  (mCharacterVM.characterList.value.isNullOrEmpty()){
            mCharacterVM.loadCharactersByGameId(gameId)
        }
        mGameVM.initGameName(gameId)
        mGameSystemVM.initGameSystemById(mGameVM.loadGameById(gameId).gameSystemId)
        /* val loadFragment = intent.getStringExtra("fragment")?:""
         val navHostFragment =
             supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
         val navController = navHostFragment.navController
         when(loadFragment){
             "CharacterMenu"->{
                 val characterId = intent.getIntExtra("characterId", 0)
                 val bundle = Bundle()
                 bundle.putInt("characterId", characterId)
                 navController.navigate(R.id.characterMenu, bundle)
             }
         }*/
    }

    fun backToMain() {
        val i = Intent(this, MainActivity::class.java)
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
}