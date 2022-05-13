package com.example.test.viewModels

import com.example.test.data_base.InitiativeFight
import io.realm.Realm

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
        nameFight: String,
        listIdCharacter: List<Int>
    ): InitiativeFight {
        val initiativeFight = InitiativeFight(
            getNewId(), nameFight, listIdCharacter
        )
        realm.executeTransaction { transactionRealm ->
            transactionRealm.insert(initiativeFight)
        }
        return initiativeFight
    }
}