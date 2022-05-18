package com.example.test.viewModels

import androidx.lifecycle.ViewModel
import com.example.test.data_base.Character
import com.example.test.data_base.GameSystem
import io.realm.Realm
import io.realm.RealmList

open class GameSystemDAO : ViewModel() {

    private var realm: Realm = Realm.getDefaultInstance()

    var currentGameSystem: GameSystem? = null

    //CREATE
    private fun getNewGameId(): Int {
        var id = realm.where(GameSystem::class.java).max("id")?.toInt()
        if (id == null) {
            id = 3
        } else {
            id += 1
        }
        return id
    }


    fun addGameSystem(gameSystem: GameSystem): Int {
        val id = getNewGameId()
        gameSystem.id = id
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(gameSystem)
        }
        currentGameSystem = gameSystem
        return id
    }

    //READ

    fun initGameSystemById(id: Int) {
        currentGameSystem = realm.where(GameSystem::class.java).equalTo("id", id).findFirst()!!
    }

    fun getTemplatesCharacter(): RealmList<Character> {
        return currentGameSystem?.templateCharacter ?: RealmList<Character>()
    }

    //UPDATE

    fun updateGameSystem(id: Int, value: String) {

    }

    //DELETE

    fun deleteGameSystemById(id: Int) {

    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}