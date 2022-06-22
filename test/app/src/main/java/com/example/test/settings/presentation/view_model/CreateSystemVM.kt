package com.example.test.settings.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.*
import io.realm.RealmList

class CreateSystemVM : ViewModel() {

    val OPTIONS: String = "OPTIONS"
    val NUM: String = "NUMBER"
    val STR: String = "STRING"

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
    // GroupParam содержит атрибуты, которые содержат параметры персонажей, типы предметов и шаблоны предметов

    val mapParamItems = mutableListOf<Pair<String, Int>>()

    val templateCharacter = mutableListOf<Character>()
    //сохранение данных, прим.: нужно лучше сохранять данные, потому что создаать систему долго, возможно не за один заход

    fun getListNameGroup(index: Int): MutableList<String?> {
        val listGPTitle = mutableListOf<String?>()
        for (gp in groups[index]) {
            listGPTitle.add(gp?.title)
        }
        return listGPTitle
    }

    // эфта функция используется в ParamCharacterSystemSettingsFragment
    fun getListsParamCharacter(): Pair<MutableList<MutableList<String?>>, MutableList<String>> {
        val list = mutableListOf<MutableList<String?>>()
        val listTitle = mutableListOf<String>()

        for (i in mapParamCharacter.toList()) {
            listTitle.add(i.first)
            val listParamName = mutableListOf<String?>()
            for (e in i.second) {
                when (e.first) {
                    STR -> {
                        val param = paramsStr[e.second]
                        listParamName.add(param.name)
                    }
                    NUM -> {
                        val param = paramsNum[e.second]
                        listParamName.add(param.name)
                    }
                    OPTIONS -> {
                        val param = paramsOptions[e.second]
                        listParamName.add(param.name)
                    }
                }
            }
            list.add(listParamName)
        }
        return Pair(list, listTitle)
    }

    // эта функция используется в ParamCharacterSystemSettingsFragment
    // она получает на вход один из трех типов параметров персонажа (см консты в этом файле) и идентификатор группы
    // и возвращает идентификатор нового параметра
    fun addParamCharacter(type: String, idGroup:Int):Int{
        when (type) {
            STR -> {
                createSystemVM.paramsStr.add(ParamStr())
                val pos = createSystemVM.paramsStr.size - 1
                createSystemVM.mapParamCharacter[title]?.add(
                    Pair(
                        createSystemVM.STR,
                        pos
                    )
                )
            }
            NUM -> {
                createSystemVM.paramsNum.add(ParamNum())
                val pos = createSystemVM.paramsNum.size - 1

                createSystemVM.mapParamCharacter[title]?.add(
                    Pair(
                        createSystemVM.NUM,
                        pos
                    )
                )
            }
            OPTIONS -> {
                createSystemVM.paramsOptions.add(
                    ParamOptions()
                )
                val pos =
                    createSystemVM.paramsOptions.size - 1
                createSystemVM.mapParamCharacter[title]?.add(
                    Pair(
                        createSystemVM.OPTIONS,
                        pos
                    )
                )
            }
        }
    }

    fun getListsParamItem(): MutableList<String?> {
        val list = mutableListOf<String?>()
        for (i in mapParamItems) {
            when (i.first) {
                STR -> {
                    val param = paramsStr[i.second]
                    list.add(param.name)
                }
                NUM -> {
                    val param = paramsNum[i.second]
                    list.add(param.name)
                }
                OPTIONS -> {
                    val param = paramsOptions[i.second]
                    list.add(param.name)
                }
            }
        }
        return list
    }

    fun getGroup(idGroup: Int): GroupParam? {
        var gp: GroupParam? = null
        for (i in groups) {
            for (e in i) {
                if (e?.id == idGroup) {
                    gp = e
                    return e
                }
            }
        }
        return gp
    }

    fun getParamStr(idGroup: Int, idParam): GroupParam? {
        var gp: GroupParam? = null
        for (i in groups) {
            for (e in i) {
                if (e?.id == idGroup) {
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