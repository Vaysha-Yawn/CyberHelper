package com.example.test.data_base.realm.game_system

import androidx.lifecycle.ViewModel
import com.example.test.data_base.realm.character.Character
import com.example.test.data_base.realm.other_realm_object.Item
import io.realm.Realm
import io.realm.RealmList

open class GameSystemDAO:ViewModel() {

    private var realm: Realm = Realm.getDefaultInstance()

    var currentGameSystem: GameSystem? = null

    //CREATE


    fun addGameSystem(gameSystem: GameSystem): Int {
        val id = realm.where(GameSystem::class.java).max("id")?.toInt()?.plus(1)?:1
        gameSystem.id = id
        realm.executeTransaction { transactionRealm ->
            transactionRealm.copyToRealm(gameSystem)
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

    fun addTemplateItem(item: Item, gameSystemId: Int, titleGroup:String) {
        val gameSystem =
            realm.where(GameSystem::class.java).equalTo("id", gameSystemId).findFirst()!!
        realm.executeTransaction { transactionRealm ->
            for (i in gameSystem.groups){
                if (i.title ==titleGroup){
                    i.attributes?.listItem?.add(item)
                }
            }
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