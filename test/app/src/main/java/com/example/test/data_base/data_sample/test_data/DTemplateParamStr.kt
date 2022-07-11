package com.example.test.data_base.data_sample.test_data

import com.example.test.data_base.realm.character.Character
import com.example.test.data_base.realm.other_realm_object.ParamStr

class DTemplateParamStr {

    val mapParamStr = mapOf<String, ParamStr>(
        "Имя персонажа" to ParamStr(
            name = "Имя персонажа",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        "Название игры" to ParamStr(name = "Название игры", value = "", removable = false),
        "Семья" to ParamStr(
            name = "Семья",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
        "Мотивация" to ParamStr(
            name = "Мотивация",
            value = "",
            removable = false,
            forItemOrCharacter = false
        ),
    )

    val mapParamStrItem = mapOf<String, ParamStr>(
        "Особенность" to ParamStr(
            name = "Особенность",
            value = "",
            removable = true,
            forItemOrCharacter = true
        ),
    )

    fun initParamStr(param: ParamStr, value: String): ParamStr {
        param.value = value
        return param
    }

    fun readParamStr(character: Character, titleGroup:String, nameParam:String):String {
        var value = ""
        character.attributes.forEach { gp ->
            if (gp.title == titleGroup) gp.attributes!!.listParamStr!!.forEach { ps ->
                if (ps.name == nameParam) {
                    value= ps.value
                }
            }
        }
        return value
    }
}