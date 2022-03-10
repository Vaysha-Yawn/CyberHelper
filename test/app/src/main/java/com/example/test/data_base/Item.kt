package com.example.test.data_base

import io.realm.RealmList
import io.realm.RealmObject

open class Item(
    var name: String = "",
    var description: String = "", // Например имплант или другое
    var removable: Boolean = true, //   Можно ли его удалить
    var effectsAdd: RealmList<EffectAdd> = RealmList<EffectAdd>(),
    var effectsWeapon: RealmList<EffectWeapon> = RealmList<EffectWeapon>(),
    var otherParamNum: RealmList<ParamNum> = RealmList<ParamNum>(),
    var otherParamStr: RealmList<ParamStr> = RealmList<ParamStr>(),
    var otherParamOptions: RealmList<ParamOptions> = RealmList<ParamOptions>(),
) : RealmObject()

class TemplateItem() {
    val mapWeapon = mapOf<String, Item>(

        "Киберрука" to Item(
            "Киберрука",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Киберрука", "Ближний бой", 1, 6))
        ),

        "Нож" to Item(
            "Нож",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Нож", "Ближний бой", 1, 6))
        ),

        "Большие костяшки" to Item(
            "Большие костяшки",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Большие костяшки", "Ближний бой", 2, 6))
        ),

        "Пистолет среднего калибра" to Item(
            "Пистолет среднего калибра",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Пистолет среднего калибра", "Дальний бой", 2, 6))
        ),

        "Пистолет-пулемёт среднего калибра" to Item(
            "Пистолет-пулемёт среднего калибра",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Пистолет-пулемёт среднего калибра", "Автоматический огонь", 2, 6))
        ),

        "Потрошители" to Item(
            "Потрошители",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Потрошители", "Ближний бой", 2, 6))
        ),

        "Моноструна" to Item(
            "Моноструна",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Моноструна", "Ближний бой", 2, 6))
        ),

        "Пистолет крупного калибра" to Item(
            "Пистолет крупного калибра",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Пистолет крупного калибра", "Дальний бой", 3, 6))
        ),

        "Пистолет сверхкрупного калибра" to Item(
            "Пистолет сверхкрупного калибра",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Пистолет сверхкрупного калибра", "Дальний бой", 4, 6))
        ),

        "Автомат" to Item(
            "Автомат",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Автомат", "Автоматический огонь", 5, 6))
        ),

        "Дробовик" to Item(
            "Дробовик",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Дробовик", "Дальний бой", 5, 6))
        ),

        "Ракетная установка" to Item(
            "Ракетная установка",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Ракетная установка", "Дальний бой", 7, 10))
        ),

        "Взрывчатка С9" to Item(
            "Взрывчатка С9",
            "",
            true,
            effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Взрывчатка С9","Взрывчатка", 8, 10))
        ),

        )

    val mapPrograms = mapOf<String, Item>(
        "Speedy Gonzalvez" to Item(
            "Speedy Gonzalvez",
            "Increases your SPD by +4 as long as this program remains rezzed.",
            true,
            effectsAdd = RealmList(EffectAdd(true, "move", 4, true, null, null)),
            otherParamNum = RealmList<ParamNum>(
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Атака программы"]!!,
                    0
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Защита программы"]!!,
                    0
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Установка программы"]!!,
                    7
                )
            )
        ),
        "Banhammer" to Item(
            "Banhammer",
            "Does 3d6 REZ to Hellhounds, 2d6 REZ to all others. Can only hurt programs.",
            false,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon("Banhammer против адских гончих",
                    "Нетраннинг против адских гончих",
                    3,
                    6,
                    null
                ), EffectWeapon("Banhammer против программ","Нетраннинг против программ", 2, 6, null)
            ),
            otherParamNum = RealmList<ParamNum>(
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Атака программы"]!!,
                    2
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Защита программы"]!!,
                    0
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Установка программы"]!!,
                    0
                )
            )
        ),
        "Flack" to Item(
            "Flack",
            "Stops the first successful non–Hellhound attack from dealing brain damage. After stopping this attack, the Flack derezzes itself.",
            true,
            otherParamNum = RealmList<ParamNum>(
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Атака программы"]!!,
                    0
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Защита программы"]!!,
                    0
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Установка программы"]!!,
                    1
                )
            )
            // хз
        ),
        "Hellhound" to Item(
            "Hellhound",
            "Does 3d6 Damage direct to a runner’s brain. Can only hurt Netrunners. Brain damage isn’t modified by location.",
            true,
            effectsWeapon = RealmList<EffectWeapon>(
                EffectWeapon("Hellhound",
                    "Нетраннинг против людей",
                    3,
                    6,
                    null
                )
            ),
            otherParamNum = RealmList<ParamNum>(
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Атака программы"]!!,
                    8
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Защита программы"]!!,
                    7
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Установка программы"]!!,
                    25
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Внимательность программы"]!!,
                    8
                ),
                TemplateParamNum().initParamNum(
                    TemplateParamNum().mapParamNumItem["Скорость программы"]!!,
                    6
                )
            )
        ),
    )

    val mapCyberware = mapOf<String, Item>(
        "Большие костяшки (пара)" to Item(
            "Большие костяшки (пара)",
            "Усиленные остяшки пальцев наносят такие же тяжёлые удары,как костеты",
            true, effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Большие костяшки (пара)", "Ближний_бой", 2, 6))
        ),
        "Киберрука (Потрошители)" to Item(
            "Киеррука (Потрошители)",
            "Киберрука, в которой спрятаны потрошители",
            true, effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Киберрука (Потрошители)","Ближний_бой", 2, 6))
        ),
        "Моноструна (одна)" to Item(
            "Моноструна (одна)",
            "Прикрепленная к пальцу проволока из моноволокна режет любые органические материалы и пластик. Может служить удавкой, резиком или кнутом",
            true, effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Моноструна (одна)", "Ближний_бой", 2, 6))
        ),
        "Потрошители (пара)" to Item(
            "Потрошители (пара)",
            "Трехдюймовые когти из карбостекла наносят режущие и колющие удары",
            true, effectsWeapon = RealmList<EffectWeapon>(EffectWeapon("Потрошители (пара)", "Ближний_бой", 2, 6))
        ),

        "Кибероптика (прицел)" to Item(
            "Кибероптика (прицел)", "Встроенный прицел дает бонус +1 к атакам дальнего боя",
            effectsAdd = RealmList<EffectAdd>(EffectAdd(true, "Дальний бой", 1, true))
        ),
    )

    val mapItem = mapOf<String, Item>(
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
    )
    fun createListItem(map: Map<String, String>): RealmList<Item> {
        val list = RealmList<Item>()
        for ((key, value) in map) {
            when (key) {
                "mapWeapon" -> {
                    if (mapWeapon[value]!=null){
                    list.add(mapWeapon[value])}
                }
                "mapPrograms" -> {
                    if (mapPrograms[value]!=null){
                    list.add(mapPrograms[value])}
                }
                "mapCyberware" -> {
                    if (mapCyberware[value]!=null){
                    list.add(mapCyberware[value])}
                }
                "mapItem" -> {
                    if (mapItem[value]!=null){
                    list.add(mapItem[value])}
                }
            }
        }
        return list
    }

    fun readItem(character: Character, titleGroup:String, nameItem:String):Item {
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
    }
}