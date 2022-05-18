package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GameSystem(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var groups: RealmList<String> = RealmList<String>(),
    var typesItem: RealmList<RealmPair<String, RealmList<String>>> = RealmList<RealmPair<String, RealmList<String>>>(),
    // где первая строка - название группы, вторая название типа
    var tabsToGroup: RealmList<RealmList<String>> = RealmList<RealmList<String>>(),
    // где порядок - номер вкладки, вторая строка - название группы, не более 5 названий, названия и иконка вкладки не изменяются, подстраивайтесь под них
    var templateCharacter: RealmList<Character> = RealmList<Character>(),
    var templateItem: RealmList<Item> = RealmList<Item>(),
    var templateParamNum: RealmList<ParamNum> = RealmList<ParamNum>(),
    var templateParamStr: RealmList<ParamStr> = RealmList<ParamStr>(),
    var templateParamOptions: RealmList<ParamOptions> = RealmList<ParamOptions>(),
    //var initiativaSystem
) : RealmObject()

open class RealmPair<K, V>(
    var key: K,
    var value: V,
) : RealmObject()
