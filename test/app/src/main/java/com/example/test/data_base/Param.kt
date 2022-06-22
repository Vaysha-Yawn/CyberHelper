package com.example.test.data_base

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Param(
    open var name: String = "",
    open var removable: Boolean = true, //   Можно ли его удалить
    open var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): RealmObject(){
    fun getCopy():Param{
        return realm.copyFromRealm(this)
    }
}

