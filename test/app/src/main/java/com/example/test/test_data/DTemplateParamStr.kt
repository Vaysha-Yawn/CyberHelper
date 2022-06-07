package com.example.test.test_data

import com.example.test.data_base.Character
import com.example.test.data_base.ParamStr

class DTemplateParamStr {

    val mapParamStr = mapOf<String, ParamStr>(
        "Имя персонажа" to ParamStr(
            "Имя персонажа",
            "",
            removable = false,
            "Базовые параметры",
            false
        ),
        "Название игры" to ParamStr("Название игры", "", removable = false),
        "Семья" to ParamStr("Семья", "", removable = false, "Биография", false),
        "Мотивация" to ParamStr("Мотивация", "", removable = false, "Биография", false),
    )

    val mapParamStrItem = mapOf<String, ParamStr>(
        "Особенность" to ParamStr("Особенность", "", removable = true, "", true),
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