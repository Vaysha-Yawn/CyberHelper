package com.example.test.settings.presentation.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.*
import com.example.test.data_base.realm.character.Character
import com.example.test.data_base.realm.other_realm_object.*
import io.realm.Realm
import io.realm.RealmList

class CreateSystemVM : ViewModel() {

    val OPTIONS: String = "OPT"
    val NUM: String = "NUMBER"
    val STR: String = "STRING"

    private val realm = Realm.getDefaultInstance()

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

    val listParamItemsStr = mutableListOf<ParamStr>()
    val listParamItemsOptions = mutableListOf<ParamOptions>()
    val listParamItemsNum = mutableListOf<ParamNum>()

    val templateCharacter = mutableListOf<Character>()
    //сохранение данных, прим.: нужно лучше сохранять данные, потому что создаать систему долго, возможно не за один заход



    fun getListNameGroup(index: Int): MutableList<String?> {
        val listGPTitle = mutableListOf<String?>()
        for (gp in groups[index]) {
            listGPTitle.add(gp?.title)
        }
        return listGPTitle
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

    fun getParamStr(titleGroup: String, idParam: Int): ParamStr? {
        var param: ParamStr? = null
        val group = getGroup(titleGroup)
        if (group?.attributes?.listParamStr != null) {
            for (i in group.attributes!!.listParamStr!!) {
                if (i.id == idParam) {
                    param = i
                }
            }
        }
        return param
    }

    fun getParamNum(titleGroup: String, idParam: Int): ParamNum? {
        var param: ParamNum? = null
        val group = getGroup(titleGroup)
        if (group?.attributes?.listParamNum != null) {
            for (i in group.attributes!!.listParamNum!!) {
                if (i.id == idParam) {
                    param = i
                }
            }
        }
        return param
    }

    fun getParamOption(titleGroup: String, idParam: Int): ParamOptions? {
        var param: ParamOptions? = null
        val group = getGroup(titleGroup)
        if (group?.attributes?.listParamOptions != null) {
            for (i in group.attributes!!.listParamOptions!!) {
                if (i.id == idParam) {
                    param = i
                }
            }
        }
        return param
    }

    // эта функция используется в ParamCharacterSystemSettingsFragment
    // нам нужен 2 MutableList<String>> - лист названий групп, которые подходят для параетров персонажа
    // нам нужен 1 MutableList<MutableList<String?>> - лист с листами названий параметров
    fun getListsParamCharacter(): Pair<MutableMap<String, MutableList<String?>>, MutableMap<String, MutableList<Pair<String, Int>>>> {
        val map = mutableMapOf<String, MutableList<String?>>()
        val mapTypeToId = mutableMapOf<String, MutableList<Pair<String, Int>>>()
        for (i in groups) {
            for (group in i) {
                if (group != null && (group.prefDD || group.prefNum || group.prefStr)) {
                    val lists = mutableListOf<String?>()
                    val listsPair = mutableListOf<Pair<String, Int>>()
                    for (str in group.attributes?.listParamStr!!) {
                        lists.add(str.name)
                        listsPair.add(Pair(STR, str.id))
                    }
                    for (num in group.attributes?.listParamNum!!) {
                        lists.add(num.name)
                        listsPair.add(Pair(NUM, num.id))
                    }
                    for (options in group.attributes?.listParamOptions!!) {
                        lists.add(options.name)
                        listsPair.add(Pair(OPTIONS, options.id))
                    }
                    map[group.title] = lists
                    mapTypeToId[group.title] = listsPair
                }
            }
        }
        return Pair(map, mapTypeToId)
    }

    // эта функция используется в ParamCharacterSystemSettingsFragment
    // она получает на вход один из трех типов параметров персонажа (см консты в этом файле) и идентификатор группы
    // и возвращает id нового параметра
    fun getNewIdParam(type: String): Int {
        var id = 0
        when (type) {
            STR -> {
                id = (realm.where(ParamStr::class.java).max("id")?.toInt() ?: 0) + 1
            }
            OPTIONS -> {
                id = (realm.where(ParamOptions::class.java).max("id")?.toInt() ?: 0) + 1
            }
            NUM -> {
                id = (realm.where(ParamNum::class.java).max("id")?.toInt() ?: 0) + 1
            }
        }
        return id

    }

    fun addParamCharacter(type: String, titleGroup: String): Int {
        val id = getNewIdParam(type)
        when (type) {
            STR -> {
                val list = getGroup(titleGroup)?.attributes?.listParamStr
                list?.add(ParamStr(id))
            }
            NUM -> {
                val list = getGroup(titleGroup)?.attributes?.listParamNum
                list?.add(ParamNum(id))
            }
            OPTIONS -> {
                val list = getGroup(titleGroup)?.attributes?.listParamOptions
                list?.add(ParamOptions(id))
            }
        }
        return id
    }

    fun delParamCharacter(title: String, idParam: Int, type: String){
        when (type) {
            STR -> {
                val param = getParamStr(title, idParam)
                getGroup(title)?.attributes?.listParamStr?.remove(param)
            }
            NUM -> {
                val param = getParamNum(title, idParam)
                getGroup(title)?.attributes?.listParamNum?.remove(param)
            }
            OPTIONS -> {
                val param = getParamOption(title, idParam)
                getGroup(title)?.attributes?.listParamOptions?.remove(param)
            }
        }
    }
    fun addParamItem(type: String): Int {
        val id = getNewIdParam(type)
        when (type) {
            STR -> {
                listParamItemsStr.add(ParamStr(id))
            }
            NUM -> {
                listParamItemsNum.add(ParamNum(id))
            }
            OPTIONS -> {
                listParamItemsOptions.add(ParamOptions(id))
            }
        }
        return id
    }

    fun delParamItem(idParam: Int, type: String){
        when (type) {
            STR -> {
                var param: ParamStr? = null
                for (i in listParamItemsStr){
                    if (i.id == idParam){
                        param = i
                    }
                }
                listParamItemsStr.remove(param)
            }
            NUM -> {
                var param: ParamNum? = null
                for (i in listParamItemsNum){
                    if (i.id == idParam){
                        param = i
                    }
                }
                listParamItemsNum.remove(param)
            }
            OPTIONS -> {
                var param: ParamOptions? = null
                for (i in listParamItemsOptions){
                    if (i.id == idParam){
                        param = i
                    }
                }
                listParamItemsOptions.remove(param)
            }
        }
    }

    fun getMapTemplateItem():MutableMap<String, MutableList<Item>>{
        val map = mutableMapOf<String, MutableList<Item>>()
        for (i in groups){
            for(group in i){
                if (group!=null && group.prefItem && group.attributes?.listItem!=null){
                    val list = mutableListOf<Item>()
                    for (item in group.attributes!!.listItem!!){
                        list.add(item)
                    }
                    map[group.title] = list
                }
            }
        }
        return map
    }

    fun getMapTypeItem():MutableMap<String, MutableList<String?>>{
        val map = mutableMapOf<String, MutableList<String?>>()
        for (i in groups){
            for(group in i){
                if (group!=null && group.prefItem && group.attributes?.listTypeItem!=null){
                    val list = mutableListOf<String?>()
                    for (item in group.attributes!!.listTypeItem!!){
                        list.add(item)
                    }
                    map[group.title] = list
                }
            }
        }
        return map
    }

    fun editTypeItem(titleGroup: String, editPos:Int, value:String){
        getGroup(titleGroup)?.attributes?.listTypeItem?.set(editPos, value)
    }

    fun getListsParamItem(): Pair<MutableList<String?>, MutableList<Pair<String, Int>>> {
        val list = mutableListOf<String?>()
        val listTI = mutableListOf<Pair<String, Int>>()
        for (str in listParamItemsStr) {
            list.add(str.name)
            listTI.add(Pair(STR, str.id))
        }
        for (num in listParamItemsNum) {
            list.add(num.name)
            listTI.add(Pair(NUM, num.id))
        }
        for (options in listParamItemsOptions) {
            list.add(options.name)
            listTI.add(Pair(OPTIONS, options.id))
        }
        return Pair(list, listTI)
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
        groups: RealmList<GroupParam>,
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