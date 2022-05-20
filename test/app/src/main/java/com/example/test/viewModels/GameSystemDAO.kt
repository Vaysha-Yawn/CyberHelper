package com.example.test.viewModels

import androidx.lifecycle.ViewModel
import com.example.test.data_base.Character
import com.example.test.data_base.GameSystem
import com.example.test.data_base.Item
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

    fun readGameSystems(): RealmList<GameSystem> {
        val list = RealmList<GameSystem>()
        for (i in realm.where(GameSystem::class.java).findAll()) {
            list.add(i)
        }
        return list
    }

    fun findGameSystemId(name: String): Int? {
        return realm.where(GameSystem::class.java).equalTo("name", name).findFirst()?.id
    }

    fun initGameSystemById(id: Int) {
        currentGameSystem = realm.where(GameSystem::class.java).equalTo("id", id).findFirst()!!
    }

    fun getTemplatesCharacter(): RealmList<Character> {
        return currentGameSystem?.templateCharacter ?: RealmList<Character>()
    }

    //UPDATE

    fun updateGameSystem(id: Int, value: String) {

    }

    fun updateTemplateItem(position: Int, item: Item, gameSystemId: Int) {
        val gameSystem =
            realm.where(GameSystem::class.java).equalTo("id", gameSystemId).findFirst()!!
        realm.executeTransaction { transactionRealm ->
            gameSystem.templateItem[position] = item
        }
    }

    fun addTemplateItem(item: Item, gameSystemId: Int) {
        val gameSystem =
            realm.where(GameSystem::class.java).equalTo("id", gameSystemId).findFirst()!!
        realm.executeTransaction { transactionRealm ->
            gameSystem.templateItem.add(item)
        }
    }

    //DELETE

    fun deleteGameSystemById(id: Int) {

    }

    override fun onCleared() {
        super.onCleared()
        realm.close()
    }

}