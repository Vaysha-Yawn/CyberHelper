package com.example.test.data_base

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ParamStr(
    @PrimaryKey
    var id:Int = 0,
    var name: String = "",
    var value: String = "",
     var removable: Boolean = true, //   Можно ли его удалить
    var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): RealmObject(){
    fun getCopy():ParamStr{
        return realm.copyFromRealm(this)
    }
}