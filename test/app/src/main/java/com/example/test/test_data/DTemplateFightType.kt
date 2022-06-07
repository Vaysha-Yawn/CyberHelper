package com.example.test.test_data

import com.example.test.data_base.FightType
import com.example.test.data_base.SettingsRoll
import com.example.test.data_base.Variation
import io.realm.RealmList

class DTemplateFightType {


    val variation = mapOf(
        "В голову" to Variation(
            effect = RealmList("difficult", "-", "6", "direct damage", "*", "2"),
            textTrue = "В голову",
            textFalse = "В тело"
        ),
        "Огонь на подавление" to Variation(
            effect = RealmList("fight type", "Огонь на подавление"),
            textTrue = "Огонь на подавление",
            textFalse = "Обычная стрельба"
        ),
        "Очередью из 3х выстрелов" to Variation(
            effect = RealmList("number shoot", "3"),
            textTrue = "Очередью из 3х выстрелов",
            textFalse = "Обычный выстрел"
        ),
    )

    val roll = SettingsRoll(
        critical = true,
        advantageAndDisadvantage = true,
        difRollByD = 10,
        modificators = true
    )

    val mapFightType = mapOf<String, FightType>(
        "Рукопашный бой" to FightType(
            name = "Рукопашный бой",
            forAllOrItem = false,
            difficult = "one roll",
            settingsRoll = roll,
            difParameters = mapOf("Параметр" to "Ловксть","Навык" to  "Уклонение"),
            roll = "one roll",
            rollParameters = mapOf("Параметр" to "Ловксть", "Навык" to  "Рукопашный бой"),
            successComment = null,
            successEffectAdd = RealmList(),
            failComment = null,
            failEffectAdd = RealmList(),
            variations = RealmList("В голову"),
            howMerge = "",
        ),
        "Бой оружием ближнего боя" to FightType(
            name = "Бой оружием ближнего боя",
            forAllOrItem = true,
            difficult = "one roll",
            settingsRoll = roll,
            difParameters = mapOf("Параметр" to "Ловксть","Навык" to "Уклонение"),
            roll = "one roll",
            rollParameters = mapOf("Параметр" to "Ловксть", "Навык" to "Оружие ближнего боя"),
            successComment = null,
            successEffectAdd = RealmList(),
            failComment = null,
            failEffectAdd = RealmList(),
            variations = RealmList("В голову"),
            howMerge = "",
        ),
        "Дальний бой" to FightType(
            name = "Дальний бой",
            forAllOrItem = true,
            difficult = "few roll",
            settingsRoll = roll,
            difParameters = mapOf("Параметр" to "Ловкость","Навык" to "Уклонение"),
            roll = "one roll",
            rollParameters = mapOf("Параметр" to "Реакция", "Навык" to "Стрельба"),
            successComment = null,
            successEffectAdd = RealmList(),
            failComment = null,
            failEffectAdd = RealmList(),
            variations = RealmList("В голову"),
            howMerge = "",
        ),
        "Дальний бой 2" to FightType(
            name = "Дальний бой 2",
            forAllOrItem = true,
            difficult = "by table",
            settingsRoll = roll,
            difParameters = mapOf("Параметр" to "Ловкость","Навык" to "Уклонение"),
            roll = "one roll",
            rollParameters = mapOf("Параметр" to "Реакция", "Навык" to "Стрельба"),
            successComment = null,
            successEffectAdd = RealmList(),
            failComment = null,
            failEffectAdd = RealmList(),
            variations = RealmList("В голову"),
            howMerge = "",
        ),
        "Автоматический огонь" to FightType(
            name = "Автоматический огонь",
            forAllOrItem = true,
            difficult = "few roll",
            settingsRoll = roll,
            difParameters = mapOf("Параметр" to "Ловкость","Навык" to "Уклонение"),
            roll = "one roll",
            rollParameters = mapOf("Параметр" to "Реакция", "Навык" to "Стрельба"),
            successComment = null,
            successEffectAdd = RealmList(),
            failComment = null,
            failEffectAdd = RealmList(),
            variations = RealmList("В голову", "Огонь на подавление", "Очередью из 3х выстрелов"),
            howMerge = "",
        ),
        )


    fun damageByTel(tel: Int): RealmList<Int> {
        val list = RealmList<Int>()
        when (tel) {
            3 -> {
                list.add(1)
                list.add(6)
            }
            4 -> {
                list.add(1)
                list.add(6)
            }
            5 -> {
                list.add(2)
                list.add(6)
            }
            6 -> {
                list.add(2)
                list.add(6)
            }
            7 -> {
                list.add(3)
                list.add(6)
            }
            8 -> {
                list.add(3)
                list.add(6)
            }
            9 -> {
                list.add(4)
                list.add(6)
            }
            10 -> {
                list.add(4)
                list.add(6)
            }
        }

        return list
    }
    // рукопашный бой
    // бой оружием брижнего боя
    // Дальний бой
    // взрысчатка С9, взрывчатка
}