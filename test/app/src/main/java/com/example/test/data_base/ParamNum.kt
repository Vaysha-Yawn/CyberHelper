package com.example.test.data_base

import io.realm.RealmObject

open class ParamNum(
    var name: String = "",
    var value: Int = 0,
    var removable: Boolean = true, //   Можно ли его удалить
    var maxValue: Int? = null,
    var minValue: Int? = null,
    var currentGroup: String = "",
    var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
) : RealmObject() {
    fun getCopy():ParamNum{
        return realm.copyFromRealm(this)
    }
}

