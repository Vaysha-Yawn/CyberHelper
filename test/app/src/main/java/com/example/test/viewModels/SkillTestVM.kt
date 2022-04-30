package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.*
import io.realm.RealmList

// для разных роллов разные классы, кроме произвольного число
data class OneRoll(
    var goal: Goal,
    var mods: MutableList<Mod>?,
    var m1d10: Int,
    var crit: Int?,
)

data class FewRolls(
    var rolls: MutableList<OneRoll>
)

class SkillTestVM : ViewModel() {
    val next = MutableLiveData<Boolean>()

    var title = ""

    /////////////////////////////////////////////////////
    var lastIndex = 0

    val mapInt = mutableMapOf<Int, MutableLiveData<Int>>()
    val mapGoal = mutableMapOf<Int, MutableLiveData<MutableList<Goal>>>()
    val mapGoalMap = mutableMapOf<Int, MutableLiveData<MutableMap<Int, Goal>>>()
    val mapMod = mutableMapOf<Int, MutableLiveData<MutableList<Mod>>>()

    lateinit var difficult: Pair<Int, String>
    lateinit var roll: Pair<Int, String>// идентификатор, указание, где искать


    val map =
        mutableMapOf<Int, MutableMap<Int, String>>()// где первое число - ключ фрагмента  2 - ключ виджет, строка - указание - в каком мапе искать
    // первые числа, 0 - fightTwo, 1 - fightThree, 2 -

    fun createId(): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        return newId
    }

    /////////////////////////////////////////////
    var luckyOrErudition: Boolean = true
    var usingLuckyPoint: Int? = null
    var skill: String = ""
    var erudit: Int? = null

    var attack: EffectWeapon? = null


    fun clearVM() {
        onCleared()
    }


    val listMore = mutableListOf<String>()// здесь после каждой проверки
    // будет сохраняться название и результат, а в конце формула
    // в конце необходимо преобразовать в Array


    // функции для рассчета сложности и броска
    // они разные

    fun findCharacterParamNum(
        characterGP: RealmList<GroupParam>?, groupName: String,
        paramName: String
    ): Int? {
        val paramValue = characterGP?.singleOrNull { gp ->
            gp.title == groupName
        }?.attributes?.listParamNum?.singleOrNull { pn ->
            pn.name == paramName
        }?.value
        return paramValue
    }

    fun calculateOneRoll(
        nameRoll: String, roll: OneRoll, parameters: Map<String, String>,
        listCharacter: RealmList<Character>
    ): Int {
        var form = "Для персонажа ${roll.goal.name} формула для подсчета суммы $nameRoll:"
        var result = 0
        val characterGP = listCharacter.singleOrNull { character ->
            character.id == roll.goal.characterId
        }?.attributes
        for ((key, value) in parameters) {
            val paramValue = findCharacterParamNum(characterGP, key, value)
            if (paramValue != null) {
                listMore.add("Характеристика $value из группы $key, для персонажа ${roll.goal.name} повлиял на $paramValue")
                result += paramValue
                form += " + $key"
            }
        }
        if (roll.mods != null) {
            if (roll.mods!!.isNotEmpty()) {
                for (mod in roll.mods!!) {
                    val res = if (mod.style) {
                        SpecialGameData().modValue[(mod.value - 1)].toInt()
                    } else {
                        mod.value
                    }
                    result -= res
                    listMore.add("Модификатор, для персонажа ${roll.goal.name} повлиял на $res")
                }
                form += " - модификатор"
            }
        }
        result += roll.m1d10
        form += " + бросок кубика"
        listMore.add("Бросок кубика персонажа ${roll.goal.name} повлиял на ${roll.m1d10}")
        if (roll.crit != null) {
            result += roll.crit!!
            listMore.add("Критический бросок кубика персонажа ${roll.goal.name} повлиял на ${roll.crit}")
            form += " + критический бросок кубика"
        }
        listMore.add(form)
        return result
    }


    fun calculateSkillTestOneRoll(
        nameRoll: String, roll: OneRoll, parameters: Map<String, String>?,
        skill: Pair<String, String>, luckyOrErudition: Boolean, usingLuckyPoint: Int?,
        erudition: Pair<String, String>?, listCharacter: RealmList<Character>
    ): Int {
        var form = "Для персонажа ${roll.goal.name} формула для подсчета суммы $nameRoll:"
        var result = 0
        val characterGP = listCharacter.singleOrNull { character ->
            character.id == roll.goal.characterId
        }?.attributes
        if (parameters != null) {
            for ((key, value) in parameters) {
                val paramValue = findCharacterParamNum(characterGP, key, value)
                if (paramValue != null) {
                    listMore.add("Характеристика $value из группы $key, для персонажа ${roll.goal.name} повлияла на $paramValue")
                    result += paramValue
                    form += " + $key"
                }
            }
        }
        val skillValue = findCharacterParamNum(characterGP, skill.first, skill.second)
        if (skillValue != null && skillValue != 0) {
            listMore.add("Характеристика ${skill.second} из группы ${skill.first}, для персонажа ${roll.goal.name} повлияла на $skillValue")
            result += skillValue
            form += " + навык"
        }
        var LEBool = luckyOrErudition
        if (skillValue == 0) {
            if (usingLuckyPoint == 0 || usingLuckyPoint == null) {
                LEBool = false
            }
            if (LEBool) {
                listMore.add("Характеристика удача, для персонажа ${roll.goal.name} повлияла на ${usingLuckyPoint ?: 0}")
                result += usingLuckyPoint ?: 0
                form += " + удача"
            } else {
                if (erudition != null) {
                    val erudit =
                        findCharacterParamNum(characterGP, erudition.first, erudition.second)
                    listMore.add("Характеристика ${erudition.second}, для персонажа ${roll.goal.name} повлияла на ${erudit ?: 0}")
                    result += erudit ?: 0
                    form += " + ${erudition.second}"
                }
            }
        }

        if (roll.mods != null) {
            if (roll.mods!!.isNotEmpty()) {
                for (mod in roll.mods!!) {
                    val res = if (mod.style) {
                        SpecialGameData().modValue[(mod.value - 1)].toInt()
                    } else {
                        mod.value
                    }
                    result -= res
                    listMore.add("Модификатор, для персонажа ${roll.goal.name} повлиял на $res")
                }
                form += " - модификатор"
            }
        }
        result += roll.m1d10
        form += " + бросок кубика"
        listMore.add("Бросок кубика персонажа ${roll.goal.name} повлиял на ${roll.m1d10}")
        if (roll.crit != null) {
            result += roll.crit!!
            listMore.add("Критический бросок кубика персонажа ${roll.goal.name} повлиял на ${roll.crit}")
            form += " + критический бросок кубика"
        }
        listMore.add(form)
        return result
    }
}

class HowToMergeFewRolls {

    fun sum(
        listRoll: List<Int>
    ): Int {// на вход принимаются уже посчитанные броски
        var i = 0
        if (listRoll.isNotEmpty()) {
            for (a in listRoll) {
                i += a
            }
        }
        return i
    }

    fun multiply(
        listRoll: List<Int>
    ): Int {// на вход принимаются уже посчитанные броски
        var i = 0
        if (listRoll.isNotEmpty()) {
            i = 1
            for (a in listRoll) {
                i *= a
            }
        }
        return i
    }

    fun rangeUp(
        listRoll: MutableMap<Int, Int>
    ): Map<Int, Int> {// на вход принимаются идентификаторы и  уже посчитанные броски
        if (listRoll.isNotEmpty()) {
            listRoll.toList().sortedBy { (_, value) -> value }.toMap()
        }
        return listRoll
    }

    fun rangeDown(
        listRoll: MutableMap<Int, Int>
    ): Map<Int, Int> {// на вход принимаются идентификаторы и  уже посчитанные броски
        if (listRoll.isNotEmpty()) {
            listRoll.toList().sortedByDescending { (_, value) -> value }.toMap()
        }
        return listRoll
    }

}