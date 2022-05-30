package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject

open class Item(
    var name: String = "",
    var description: String = "", // Например имплант или другое
    var removable: Boolean = true, //   Можно ли его удалить
    var effectsAdd: RealmList<EffectAdd> = RealmList<EffectAdd>(),
    var effectsDamage: RealmList<EffectDamage> = RealmList<EffectDamage>(),
    var otherParamNum: RealmList<ParamNum> = RealmList<ParamNum>(),
    var otherParamStr: RealmList<ParamStr> = RealmList<ParamStr>(),
    var otherParamOptions: RealmList<ParamOptions> = RealmList<ParamOptions>(),
    var group: String = "",
    var type: String = ""
) : RealmObject(){
    fun getCopy():Item{
        return realm.copyFromRealm(this)
    }
}

class DTemplateItem {

    val melle = "Ближний бой"
    val distant_battle = "Дальний бой"
    val automatic_fire = "Автоматический огонь"
    val explosive = "Взрывчатка"

    val mapGroupToItems = mutableMapOf(
        "Оружие" to mutableMapOf<String, Item>(

            "Киберрука" to Item(
                "Киберрука",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage("Киберрука", melle, 1, 6))
            ),

            "Нож" to Item(
                "Нож",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage("Нож", melle, 1, 6))
            ),

            "Большие костяшки" to Item(
                "Большие костяшки",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Большие костяшки",
                        melle,
                        2,
                        6
                    )
                )
            ),

            "Пистолет среднего калибра" to Item(
                "Пистолет среднего калибра",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет среднего калибра",
                        distant_battle,
                        2,
                        6
                    )
                )
            ),

            "Пистолет-пулемёт среднего калибра" to Item(
                "Пистолет-пулемёт среднего калибра",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет-пулемёт среднего калибра",
                        automatic_fire,
                        2,
                        6
                    )
                )
            ),

            "Потрошители" to Item(
                "Потрошители",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage("Потрошители", melle, 2, 6))
            ),

            "Моноструна" to Item(
                "Моноструна",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage("Моноструна", melle, 2, 6))
            ),

            "Пистолет крупного калибра" to Item(
                "Пистолет крупного калибра",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет крупного калибра",
                        distant_battle,
                        3,
                        6
                    )
                )
            ),

            "Пистолет сверхкрупного калибра" to Item(
                "Пистолет сверхкрупного калибра",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет сверхкрупного калибра",
                        distant_battle,
                        4,
                        6
                    )
                )
            ),

            "Автомат" to Item(
                "Автомат",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Автомат",
                        automatic_fire,
                        5,
                        6
                    )
                )
            ),

            "Дробовик" to Item(
                "Дробовик",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Дробовик",
                        distant_battle,
                        5,
                        6
                    )
                )
            ),

            "Ракетная установка" to Item(
                "Ракетная установка",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Ракетная установка",
                        distant_battle,
                        7,
                        10
                    )
                )
            ),

            "Взрывчатка С9" to Item(
                "Взрывчатка С9",
                "",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Взрывчатка С9",
                        explosive,
                        8,
                        10
                    )
                )
            ),

            ),
        "Киберимпланты" to mutableMapOf<String, Item>(
            "Большие костяшки (пара)" to Item(
                "Большие костяшки (пара)",
                "Усиленные остяшки пальцев наносят такие же тяжёлые удары,как костеты",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Большие костяшки (пара)",
                        melle,
                        2,
                        6
                    )
                )
            ),
            "Киберрука (Потрошители)" to Item(
                "Киеррука (Потрошители)",
                "Киберрука, в которой спрятаны потрошители",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Киберрука (Потрошители)",
                        melle,
                        2,
                        6
                    )
                )
            ),
            "Моноструна (одна)" to Item(
                "Моноструна (одна)",
                "Прикрепленная к пальцу проволока из моноволокна режет любые органические материалы и пластик. Может служить удавкой, резиком или кнутом",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Моноструна (одна)",
                        melle,
                        2,
                        6
                    )
                )
            ),
            "Потрошители (пара)" to Item(
                "Потрошители (пара)",
                "Трехдюймовые когти из карбостекла наносят режущие и колющие удары",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Потрошители (пара)",
                        melle,
                        2,
                        6
                    )
                )
            ),

            "Кибероптика (прицел)" to Item(
                "Кибероптика (прицел)", "Встроенный прицел дает бонус +1 к атакам дальнего боя",
                effectsAdd = RealmList<EffectAdd>(EffectAdd(true, "Дальний бой", 1, true))
            ),
        ),

        "Снаряжение" to mutableMapOf<String, Item>(
            "100 евробаксов немаркированными купюрами" to Item(
                "100 евробаксов немаркированными купюрами", "money", true,
                effectsAdd = RealmList<EffectAdd>(EffectAdd(false, "Деньги, евробаксы", 100, true))
            ),
            "Agent w/ Pseudo AI Secretary" to Item(
                "Agent w/ Pseudo AI Secretary",
                "A pocket sized machine which functions as a computer and a phone. This Agent has a secretary program.",
                true
            ),
            "Agent" to Item(
                "Agent",
                "A pocket-sized machine which functions as a computer and a phone.",
                true
            ),
            "Guitar" to Item(
                "Guitar",
                "Forty’s instrument of choice and most prized possession.",
                true
            ),
            "Поступок" to Item(
                "Название поступка",
                "Описание поступка",
                true,
                effectsAdd = RealmList<EffectAdd>(
                    EffectAdd(true, "Итого репутация", 1, true)
                )
            ),
        ),
        "Программы" to mutableMapOf<String, Item>(
            "Speedy Gonzalvez" to Item(
                "Speedy Gonzalvez",
                "Increases your SPD by +4 as long as this program remains rezzed.",
                true,
                effectsAdd = RealmList(EffectAdd(true, "move", 4, true, null, null)),
                otherParamNum = RealmList<ParamNum>(
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Атака программы"]!!,
                        0
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Защита программы"]!!,
                        0
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Установка программы"]!!,
                        7
                    )
                )
            ),
            "Banhammer" to Item(
                "Banhammer",
                "Does 3d6 REZ to Hellhounds, 2d6 REZ to all others. Can only hurt programs.",
                false,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Banhammer против адских гончих",
                        "Нетраннинг против адских гончих",
                        3,
                        6
                    ),
                    EffectDamage(
                        "Banhammer против программ",
                        "Нетраннинг против программ",
                        2,
                        6
                    )
                ),
                otherParamNum = RealmList<ParamNum>(
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Атака программы"]!!,
                        2
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Защита программы"]!!,
                        0
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Установка программы"]!!,
                        0
                    )
                )
            ),
            "Flack" to Item(
                "Flack",
                "Stops the first successful non–Hellhound attack from dealing brain damage. After stopping this attack, the Flack derezzes itself.",
                true,
                otherParamNum = RealmList<ParamNum>(
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Атака программы"]!!,
                        0
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Защита программы"]!!,
                        0
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Установка программы"]!!,
                        1
                    )
                )
                // хз
            ),
            "Hellhound" to Item(
                "Hellhound",
                "Does 3d6 Damage direct to a runner’s brain. Can only hurt Netrunners. Brain damage isn’t modified by location.",
                true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Hellhound",
                        "Нетраннинг против людей",
                        3,
                        6
                    )
                ),
                otherParamNum = RealmList<ParamNum>(
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Атака программы"]!!,
                        8
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Защита программы"]!!,
                        7
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Установка программы"]!!,
                        25
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Внимательность программы"]!!,
                        8
                    ),
                    DTemplateParamNum().initParamNum(
                        DTemplateParamNum().mapParamNumItem["Скорость программы"]!!,
                        6
                    )
                )
            ),
        ),

        )

    val mapWeapon = mapGroupToItems["Оружие"]

    val mapPrograms = mapGroupToItems["Программы"]

    val mapCyberware = mapGroupToItems["Киберимпланты"]

    val mapItem = mapGroupToItems["Снаряжение"]

    /*fun createListItem(map: Map<String, String>): RealmList<Item> {
        val list = RealmList<Item>()
        for ((key, value) in map) {
            when (key) {
                "mapWeapon" -> {
                    if (mapWeapon?.get(value) != null) {
                        list.add(mapWeapon[value])
                    }
                }
                "mapPrograms" -> {
                    if (mapPrograms?.get(value) != null) {
                        list.add(mapPrograms[value])
                    }
                }
                "mapCyberware" -> {
                    if (mapCyberware?.get(value) != null) {
                        list.add(mapCyberware[value])
                    }
                }
                "mapItem" -> {
                    if (mapItem?.get(value) != null) {
                        list.add(mapItem[value])
                    }
                }
            }
        }
        return list
    }*/

    /*fun readItem(character: Character, titleGroup:String, nameItem:String):Item {
        var value = Item()
        character.attributes.forEach { gp ->
            if (gp.title == titleGroup) {
                gp.attributes!!.listItem.forEach { item ->
                    if (item.name == nameItem) {
                        value= item
                    }
                }
            }
        }
        return value
    }*/
}