package com.example.test.data_base.realm.character

import com.example.test.data_base.realm.other_realm_object.GroupParam
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// основной объект для игрового персонажа
open class Character(
    @PrimaryKey
    var id: Int = 0,
    var gameId: Int = 0,
    var attributes: RealmList<GroupParam> = RealmList<GroupParam>(),
) : RealmObject() {
    fun getCopy(): Character {
        return realm.copyFromRealm(this)
    }
}

