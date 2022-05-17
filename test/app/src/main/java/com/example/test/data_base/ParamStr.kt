package com.example.test.data_base

import io.realm.RealmObject

open class ParamStr(
    var name: String = "",
    var value: String = "",
    var removable: Boolean = true, //   Можно ли его удалить
    var currentGroup: String = "",
    var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): RealmObject()

