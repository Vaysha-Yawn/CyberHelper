package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GameSystem(
    @PrimaryKey
    var id: Int = 2,
    var name: String = "",
    var groups: RealmList<GroupParam> = RealmList<GroupParam>(),
    var typesItem: RealmList<RealmPair> = RealmList<RealmPair>(),
    // где первая строка - название группы, вторая название типа
    var typesAttack: RealmList<String> = RealmList<String>(),
    // где первая строка - название группы, вторая название типа
    var tabsToGroup: RealmList<RealmSomething> = RealmList<RealmSomething>(),
    // где порядок - номер вкладки, вторая строка - название группы, не более 5 названий, названия и иконка вкладки не изменяются, подстраивайтесь под них
    var templateCharacter: RealmList<Character> = RealmList<Character>(),
    var templateItem: RealmList<Item> = RealmList<Item>(),
    var templateParamNum: RealmList<ParamNum> = RealmList<ParamNum>(),
    var templateParamStr: RealmList<ParamStr> = RealmList<ParamStr>(),
    var templateParamOptions: RealmList<ParamOptions> = RealmList<ParamOptions>(),
    //var initiativaSystem
) : RealmObject()

open class RealmPair(
    var key: String = "",
    var value: RealmList<String> = RealmList(),
) : RealmObject()

open class RealmSomething(
    var key: RealmList<String> = RealmList()
) : RealmObject()

