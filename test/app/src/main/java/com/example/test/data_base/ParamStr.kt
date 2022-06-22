package com.example.test.data_base

import io.realm.annotations.PrimaryKey

open class ParamStr(
    @PrimaryKey
    var id: Int = 0,
    override var name: String = "",
    var value: String = "",
    override var removable: Boolean = true, //   Можно ли его удалить
    override var forItemOrCharacter: Boolean = false, //  true - Item, false - Character
): Param()