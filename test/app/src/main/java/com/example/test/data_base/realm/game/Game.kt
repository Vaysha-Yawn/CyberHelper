package com.example.test.data_base.realm.game

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

// объект игры
open class Game(
    @PrimaryKey
    var id: Int = 0,
    var name: String? = null,
    var gameSystemId: Int = 0,
): RealmObject()