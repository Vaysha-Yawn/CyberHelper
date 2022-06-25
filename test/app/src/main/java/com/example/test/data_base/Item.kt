package com.example.test.data_base

import com.example.test.test_data.DTemplateParamNum
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Item(
    @PrimaryKey
    var id:Int = 0,
    var name: String = "",
    var description: String = "", // Например имплант или другое
    var removable: Boolean = true, //   Можно ли его удалить
    var effectsAdd: RealmList<EffectAdd> = RealmList<EffectAdd>(),
    var effectsDamage: RealmList<EffectDamage> = RealmList<EffectDamage>(),
    var otherParamNum: RealmList<ParamNum> = RealmList<ParamNum>(),
    var otherParamStr: RealmList<ParamStr> = RealmList<ParamStr>(),
    var otherParamOptions: RealmList<ParamOptions> = RealmList<ParamOptions>(),
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
                name = "Киберрука",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage(
                    "Киберрука",
                    melle,
                    numCount = 1,
                    dX = 6
                ))
            ),

            "Нож" to Item(
                name = "Нож",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage(
                    "Нож",
                    melle,
                    numCount = 1,
                    dX = 6
                ))
            ),

            "Большие костяшки" to Item(
                name = "Большие костяшки",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Большие костяшки",
                        melle,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),

            "Пистолет среднего калибра" to Item(
                name = "Пистолет среднего калибра",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет среднего калибра",
                        distant_battle,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),

            "Пистолет-пулемёт среднего калибра" to Item(
                name = "Пистолет-пулемёт среднего калибра",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет-пулемёт среднего калибра",
                        automatic_fire,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),

            "Потрошители" to Item(
                name = "Потрошители",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage(
                    "Потрошители",
                    melle,
                    numCount = 2,
                    dX = 6
                ))
            ),

            "Моноструна" to Item(
                name = "Моноструна",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(EffectDamage(
                    "Моноструна",
                    melle,
                    numCount = 2,
                    dX = 6
                ))
            ),

            "Пистолет крупного калибра" to Item(
                name = "Пистолет крупного калибра",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет крупного калибра",
                        distant_battle,
                        numCount = 3,
                        dX = 6
                    )
                )
            ),

            "Пистолет сверхкрупного калибра" to Item(
                name = "Пистолет сверхкрупного калибра",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Пистолет сверхкрупного калибра",
                        distant_battle,
                        numCount = 4,
                        dX = 6
                    )
                )
            ),

            "Автомат" to Item(
                name = "Автомат",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Автомат",
                        automatic_fire,
                        numCount = 5,
                        dX = 6
                    )
                )
            ),

            "Дробовик" to Item(
                name = "Дробовик",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Дробовик",
                        distant_battle,
                        numCount = 5,
                        dX = 6
                    )
                )
            ),

            "Ракетная установка" to Item(
                name = "Ракетная установка",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Ракетная установка",
                        distant_battle,
                        numCount = 7,
                        dX = 10
                    )
                )
            ),

            "Взрывчатка С9" to Item(
                name = "Взрывчатка С9",
                description = "",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Взрывчатка С9",
                        explosive,
                        numCount = 8,
                        dX = 10
                    )
                )
            ),

            ),
        "Киберимпланты" to mutableMapOf<String, Item>(
            "Большие костяшки (пара)" to Item(
                name = "Большие костяшки (пара)",
                description = "Усиленные остяшки пальцев наносят такие же тяжёлые удары,как костеты",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Большие костяшки (пара)",
                        melle,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),
            "Киберрука (Потрошители)" to Item(
                name = "Киеррука (Потрошители)",
                description = "Киберрука, в которой спрятаны потрошители",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Киберрука (Потрошители)",
                        melle,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),
            "Моноструна (одна)" to Item(
                name = "Моноструна (одна)",
                description = "Прикрепленная к пальцу проволока из моноволокна режет любые органические материалы и пластик. Может служить удавкой, резиком или кнутом",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Моноструна (одна)",
                        melle,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),
            "Потрошители (пара)" to Item(
                name = "Потрошители (пара)",
                description = "Трехдюймовые когти из карбостекла наносят режущие и колющие удары",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Потрошители (пара)",
                        melle,
                        numCount = 2,
                        dX = 6
                    )
                )
            ),

            "Кибероптика (прицел)" to Item(
                name = "Кибероптика (прицел)",
                description = "Встроенный прицел дает бонус +1 к атакам дальнего боя",
                effectsAdd = RealmList<EffectAdd>(EffectAdd(true, "Дальний бой", 1, true))
            ),
        ),

        "Снаряжение" to mutableMapOf<String, Item>(
            "100 евробаксов немаркированными купюрами" to Item(
                name = "100 евробаксов немаркированными купюрами",
                description = "money",
                removable = true,
                effectsAdd = RealmList<EffectAdd>(EffectAdd(false, "Деньги, евробаксы", 100, true))
            ),
            "Agent w/ Pseudo AI Secretary" to Item(
                name = "Agent w/ Pseudo AI Secretary",
                description = "A pocket sized machine which functions as a computer and a phone. This Agent has a secretary program.",
                removable = true
            ),
            "Agent" to Item(
                name = "Agent",
                description = "A pocket-sized machine which functions as a computer and a phone.",
                removable = true
            ),
            "Guitar" to Item(
                name = "Guitar",
                description = "Forty’s instrument of choice and most prized possession.",
                removable = true
            ),
            "Поступок" to Item(
                name = "Название поступка",
                description = "Описание поступка",
                removable = true,
                effectsAdd = RealmList<EffectAdd>(
                    EffectAdd(true, "Итого репутация", 1, true)
                )
            ),
        ),
        "Программы" to mutableMapOf<String, Item>(
            "Speedy Gonzalvez" to Item(
                name = "Speedy Gonzalvez",
                description = "Increases your SPD by +4 as long as this program remains rezzed.",
                removable = true,
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
                name = "Banhammer",
                description = "Does 3d6 REZ to Hellhounds, 2d6 REZ to all others. Can only hurt programs.",
                removable = false,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Banhammer против адских гончих",
                        "Нетраннинг против адских гончих",
                        numCount = 3,
                        dX = 6
                    ),
                    EffectDamage(
                        "Banhammer против программ",
                        "Нетраннинг против программ",
                        numCount = 2,
                        dX = 6
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
                name = "Flack",
                description = "Stops the first successful non–Hellhound attack from dealing brain damage. After stopping this attack, the Flack derezzes itself.",
                removable = true,
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
                name = "Hellhound",
                description = "Does 3d6 Damage direct to a runner’s brain. Can only hurt Netrunners. Brain damage isn’t modified by location.",
                removable = true,
                effectsDamage = RealmList<EffectDamage>(
                    EffectDamage(
                        "Hellhound",
                        "Нетраннинг против людей",
                        numCount = 3,
                        dX = 6
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