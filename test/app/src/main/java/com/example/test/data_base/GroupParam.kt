package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class GroupParam(
    var title: String = "",
    var attributes: GroupAttributes? = GroupAttributes(),
    // может убрать префы и ориентироваться на то есть ли лист даже пустой или он равен нулл?
    var prefNum:Boolean = true,
    var prefStr:Boolean = true,
    var prefDD:Boolean = true,
    var prefItem:Boolean = true,
) : RealmObject()

open class GroupAttributes(
    var listParamNum: RealmList<ParamNum>? = RealmList<ParamNum>(),
    var listParamStr: RealmList<ParamStr>? = RealmList<ParamStr>(),
    var listParamOptions: RealmList<ParamOptions>? = RealmList<ParamOptions>(),
    var listTypeItem: RealmList<String>? = RealmList<String>(),
    var listItem: RealmList<Item>? = RealmList<Item>(),
) : RealmObject()


/*class TemplateCharacter {

    *//*fun initGroupsParam(map: Map<String, Map<String, Map<String, String>>>): RealmList<GroupParam> {
        val list = RealmList<GroupParam>()
        for ((key, value) in map) {
            val gp = GroupParam()
            gp.title = key
            gp.attributes = initGroupAttributes(value)
            list.add(gp)
        }
        return list
    }*//*

    *//*fun initGroupAttributes(
        map: Map<String, Map<String, String>>
    ): GroupAttributes {
        val ga = GroupAttributes()
        for ((key, value) in map) {
            when (key) {
                "listParamStr" -> {
                    ga.listParamStr = TemplateParamStr().createListParamStr(value)
                }
                "listParamNum" -> {
                    ga.listParamNum = DTemplateParamNum().createListParamNum(value)
                }
                "listParamOptions" -> {
                    ga.listParamOptions = DTemplateParamOptions().createListParamOptions(value)
                }
                "listItem" -> {
                    ga.listItem = TemplateItem().createListItem(value)
                }
            }

        }
        return ga
    }*//*

    val mapCharacter = mapOf<String, Character>(
        "Гриз" to Character(
            0, 0,
            attributes = initGroupsParam(
                mapOf(
                    "Базовые параметры" to mapOf(//название группы
                        "listParamStr" to mapOf(//тип листа
                            "Имя персонажа" to "Гриз"//название параметра и значение
                        ),
                        "listParamNum" to mapOf(
                            "Возраст" to "30",
                            "Здоровье" to "25",
                            "Тяжелое ранение" to "13",
                            "Испытание против смерти" to "5",
                            "Деньги, евробаксы" to "0",
                        ),
                        "listParamOptions" to mapOf(
                            "Значимость" to "Главный герой",
                            "Роль" to "Фиксер",
                            "Пол" to "Мужской",
                        )
                    ),
                    "Броня" to mapOf(//название группы
                        "listParamNum" to mapOf(
                            "Броня для головы" to "11",
                            "Броня для тела" to "11",
                        ),
                    ),
                    "Оружие" to mapOf(//название группы
                        "listItem" to mapOf(
                            "mapWeapon" to "Пистолет крупного калибра",
                            "mapWeapon" to "Пистолет-пулемёт среднего калибра",
                        ),
                    ),
                    "Параметры" to mapOf(//название группы
                        "listParamNum" to mapOf(
                            "Интеллект" to "7",
                        ),
                    ),
                    "Навыки" to mapOf(//название группы
                        "listParamNum" to mapOf(
                            "Атлетика" to "4",
                        ),
                    ),
                    "Киберимпланты" to mapOf(//название группы
                        "listItem" to mapOf(
                            "mapCyberware" to "Кибероптика (прицел)",
                        ),
                    ),
                    "Снаряжение" to mapOf(//название группы
                        "listItem" to mapOf(
                            "mapItem" to "100 евробаксов немаркированными купюрами",
                        ),
                    ),
                    "Программы" to mapOf(//название группы
                        "listItem" to mapOf(
                        ),
                    ),
                    "Репутация" to mapOf(//название группы
                        "listItem" to mapOf(
                            "mapItem" to "Поступок",
                        ),
                        "listParamNum" to mapOf(
                            "Итого репутация" to "0",
                        ),
                    ),
                    "Биография" to mapOf(//название группы
                        "listParamStr" to mapOf(
                            "Семья" to "",
                            "Мотивация" to "",
                        ),
                    ),
                )
            )
        )
    )

    val characterLisToGroupParam = mapOf(
        1 to listOf(
            "Базовые параметры"
        ),
        2 to listOf(
            "Параметры",
            "Навыки"
        ),
        3 to listOf(
            "Броня",
            "Оружие"
        ),
        4 to listOf(
            "Киберимпланты",
            "Снаряжение",
            "Программы"
        ),
        5 to listOf(
            "Репутация",
            "Биография"
        ),
    )

}*/