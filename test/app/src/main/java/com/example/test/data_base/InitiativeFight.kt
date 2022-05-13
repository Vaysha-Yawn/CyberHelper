package com.example.test.data_base

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class InitiativeFight(
    @PrimaryKey
    var id: Int = 0,
    var gameId: Int = 0,
    var nameFight: String = "",
    var listIdCharacter: List<Int> = mutableListOf()
): RealmObject()