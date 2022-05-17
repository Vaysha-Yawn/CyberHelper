package com.example.test.viewModels

import androidx.lifecycle.ViewModel
import com.example.test.data_base.GameSystem
import io.realm.Realm

open class GameSystemDAO : ViewModel() {

    private var realm: Realm = Realm.getDefaultInstance()

    var currentGameSystem = GameSystem()

    //CREATE

    fun addGameSystem(gameSystem: GameSystem) {
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(gameSystem)
        }
    }

    //READ

    fun initGameSystemById(id: Int) {
        currentGameSystem = realm.where(GameSystem::class.java).equalTo("id", id).findFirst()!!
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