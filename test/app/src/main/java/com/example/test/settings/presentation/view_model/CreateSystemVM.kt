package com.example.test.settings.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.*
import io.realm.RealmList

class CreateSystemVM : ViewModel() {

    val OPTIONS:String = "OPTIONS"
    val NUM:String = "NUMBER"
    val STR:String = "STRING"

    // отображение, данные для RV
    var name = ""
    val typesDamage = MutableLiveData<MutableList<String?>>(mutableListOf())
    val groups = listOf<MutableList<GroupParam?>>(
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
        mutableListOf(),
    )

    val typesItems = mutableMapOf<String, MutableList<String?>>()

    val characterParamsNum = mutableListOf<ParamNum>()
    val characterParamsStr = mutableListOf<ParamStr>()
    val characterParamsOptions = mutableListOf<ParamOptions>()
    val mapParamCharacter =
        mutableMapOf<String, MutableList<Pair<String, Int>>>()// где мапа название группы - пара(тип, позиция)

    val itemParams = MutableLiveData<MutableList<String>>(mutableListOf())
    val templateItems = mutableMapOf<String, MutableLiveData<MutableList<String>>>()
    val templateCharacter = MutableLiveData(mutableListOf<Character>())
    //сохранение данных, прим.: нужно лучше сохранять данные, потому что создаать систему долго, возможно не за один заход

    fun getListNameGroup() {

    }

    fun getGroup(title: String): GroupParam? {
        var gp: GroupParam? = null
        for (i in groups) {
            for (e in i) {
                if (e?.title == title) {
                    gp = e
                    return e
                }
            }
        }
        return gp
    }


    fun <T> listToRealmList(
        list: MutableList<T>
    ): RealmList<T> {
        val realmList = RealmList<T>()
        for (i in list) {
            realmList.add(i)
        }
        return realmList
    }

    fun getSystem(
        name: String,
        groups: RealmList<String>,
        typesItem: RealmList<RealmPair>,
        typesAttack: RealmList<String>,
        tabsToGroup: RealmList<RealmSomething>,
        templateCharacter: RealmList<Character>,
        templateItem: RealmList<Item>,
        templateParamNum: RealmList<ParamNum>,
        templateParamStr: RealmList<ParamStr>,
        templateParamOptions: RealmList<ParamOptions>
    ): GameSystem {
        return GameSystem(
            0, name, groups, typesItem, typesAttack, tabsToGroup, templateCharacter,
            templateItem, templateParamNum, templateParamStr, templateParamOptions
        )
    }


}