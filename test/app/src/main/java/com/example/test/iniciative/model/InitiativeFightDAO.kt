package com.example.test.iniciative.model

import io.realm.Realm
import io.realm.RealmList

class InitiativeFightDAO {

    private var realm: Realm = Realm.getDefaultInstance()

    private fun getNewId(): Int {
        var id = realm.where(InitiativeFight::class.java).max("id")?.toInt()
        if (id == null) {
            id = 1
        } else {
            id += 1
        }
        return id
    }

    fun addInitiativeFight(
        gameId:Int,
        nameFight: String,
        listIdCharacter: RealmList<Int>
    ): InitiativeFight {
        val initiativeFight = InitiativeFight(
            getNewId(), gameId, nameFight = nameFight, listIdCharacter = listIdCharacter
        )
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(initiativeFight)
        }
        return initiativeFight
    }

    fun loadByGameId(gameId: Int): RealmList<InitiativeFight> {
        val list = RealmList<InitiativeFight>()
        val res = realm.where(InitiativeFight::class.java).equalTo("gameId", gameId).findAll()
        res.forEach {
            list.add(it)
        }
        return list
    }

    fun deleteInitiativeFight(id: Int) {
        realm.executeTransaction {
            val initiativeFightRealm = realm.where(InitiativeFight::class.java).equalTo("id", id).findFirst()
            initiativeFightRealm?.deleteFromRealm()
        }
    }

    fun endMove(characterId:Int, iniciativeFightId:Int) {
        realm.executeTransaction {
            val initiativeFightRealm = realm.where(InitiativeFight::class.java).equalTo("id", iniciativeFightId).findFirst()
            initiativeFightRealm?.listIdCharacter?.remove(characterId)
            initiativeFightRealm?.listIdCharacter?.add(characterId)
        }
    }
}