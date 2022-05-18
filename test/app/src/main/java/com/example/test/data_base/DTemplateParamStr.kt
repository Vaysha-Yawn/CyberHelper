package com.example.test.data_base

import io.realm.RealmList

class DTemplateParamStr {

    val mapParamStr = mapOf<String, ParamStr>(
        "Имя персонажа" to ParamStr("Имя персонажа", "", removable = false, "Базовые параметры"),
        "Название игры" to ParamStr("Название игры", "", removable = false, ""),
        "Семья" to ParamStr("Семья", "", removable = false),
        "Мотивация" to ParamStr("Мотивация", "", removable = false),
    )

    val mapParamStrItem = mapOf<String, ParamStr>(
        "Особенность" to ParamStr("Особенность", "", removable = true),
    )

    fun initParamStr(param: ParamStr, value: String): ParamStr {
        param.value = value
        return param
    }

    fun createListParamStr(map: Map<String, String>): RealmList<ParamStr> {
        val list = RealmList<ParamStr>()
        for ((key,value) in map){
            if (mapParamStr[key]!=null){
            list.add(initParamStr(mapParamStr[key]!!, value))}
        }
        return list
    }

    fun readParamStr(character: Character, titleGroup:String, nameParam:String):String {
        var value = ""
        character.attributes.forEach { gp ->
            if (gp.title == titleGroup) {
                gp.attributes!!.listParamStr.forEach { ps ->
                    if (ps.name == nameParam) {
                        value= ps.value
                    }
                }
            }
        }
        return value
    }
}