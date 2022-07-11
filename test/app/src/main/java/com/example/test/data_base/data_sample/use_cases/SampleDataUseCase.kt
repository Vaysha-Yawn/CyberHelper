package com.example.test.data_base.data_sample.use_cases

import com.example.test.data_base.realm.other_realm_object.*
import io.realm.RealmList

class SampleDataUseCase {

    fun initGroupsParam(
        map: Map<String, Map<String, Map<String, String>>>,
        listStr: RealmList<ParamStr>,
        listNum: RealmList<ParamNum>,
        listOptions: RealmList<ParamOptions>,
        listItem: RealmList<Item>,
    ): RealmList<GroupParam> {
        val list = RealmList<GroupParam>()
        for ((key, value) in map) {
            val gp = GroupParam()
            gp.title = key
            gp.attributes = initGroupAttributes(value, listStr, listNum, listOptions, listItem)
            list.add(gp)
        }
        return list
    }

    private fun initGroupAttributes(
        map: Map<String, Map<String, String>>,
        listStr: RealmList<ParamStr>,
        listNum: RealmList<ParamNum>,
        listOptions: RealmList<ParamOptions>,
        listItem: RealmList<Item>,
    ): GroupAttributes {
        val ga = GroupAttributes()
        for ((key, value) in map) {
            when (key) {
                "listParamStr" -> {
                    ga.listParamStr = setValueListStr(value, listStr)
                }
                "listParamNum" -> {
                    ga.listParamNum = setValueListNum(value, listNum)
                }
                "listParamOptions" -> {
                    ga.listParamOptions = setValueListOptions(value, listOptions)
                }
                "listItem" -> {
                    ga.listItem = createListItem(value, listItem)
                }
            }

        }
        return ga
    }

    private fun findParamStr(name: String, list: RealmList<ParamStr>): ParamStr {
        return list.singleOrNull {
            it.name == name
        } ?: ParamStr()
    }

    private fun findParamNum(name: String, list: RealmList<ParamNum>): ParamNum {
        return list.singleOrNull {
            it.name == name
        } ?: ParamNum()
    }

    private fun findParamOptions(name: String, list: RealmList<ParamOptions>): ParamOptions {
        return list.singleOrNull {
            it.name == name
        } ?: ParamOptions()
    }

    private fun findItem(name: String, list: RealmList<Item>): Item {
        return list.singleOrNull {
            it.name == name
        } ?: Item()
    }

    private fun setValueForParamStr(param: ParamStr, value: String): ParamStr {
        param.value = value
        return param
    }

    private fun setValueForParamNum(param: ParamNum, value: String): ParamNum {
        param.value = value.toInt()
        return param
    }

    private fun setValueForParamOptions(param: ParamOptions, value: String): ParamOptions {
        param.value = value
        return param
    }

    private fun setValueListStr(
        map: Map<String, String>,
        templateList: RealmList<ParamStr>
    ): RealmList<ParamStr> {
        val list = RealmList<ParamStr>()
        for ((key, value) in map) {
            if (findParamStr(key, templateList) != ParamStr()) {
                list.add(setValueForParamStr(findParamStr(key, templateList), value))
            }
        }
        return list
    }

    private fun createListItem(
        map: Map<String, String>,
        templateList: RealmList<Item>
    ): RealmList<Item> {
        val list = RealmList<Item>()
        for ((key, value) in map) {
            findItem(value, templateList)
        }
        return list
    }

    private fun setValueListNum(
        map: Map<String, String>,
        templateList: RealmList<ParamNum>
    ): RealmList<ParamNum> {
        val list = RealmList<ParamNum>()
        for ((key, value) in map) {
            if (findParamNum(key, templateList) != ParamNum()) {
                list.add(setValueForParamNum(findParamNum(key, templateList), value))
            }
        }
        return list
    }

    private fun setValueListOptions(
        map: Map<String, String>,
        templateList: RealmList<ParamOptions>
    ): RealmList<ParamOptions> {
        val list = RealmList<ParamOptions>()
        for ((key, value) in map) {
            if (findParamOptions(key, templateList) != ParamOptions()) {
                list.add(setValueForParamOptions(findParamOptions(key, templateList), value))
            }
        }
        return list
    }
}
