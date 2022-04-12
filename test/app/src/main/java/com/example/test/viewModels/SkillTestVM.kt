package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.EffectWeapon
import com.example.test.data_base.Goal
import com.example.test.data_base.Mod

class SkillTestVM:ViewModel() {

    var title = ""

    val modification =
        MutableLiveData<MutableList<Mod>>() // mod.value = -1 , оно сохраняется с индексом на 1 больше, чтобы оставить при создании начальную фразу
    val allGoals = MutableLiveData<MutableList<Goal>>()
    val chosenGoals = MutableLiveData<MutableList<Goal>>()
    val m1d10 = MutableLiveData<Int>()
    val critical = MutableLiveData<Int>()
    val boolCritical = MutableLiveData<Boolean>()
    val deletedIdByGoal = mutableListOf<Int>()
    val dif = MutableLiveData<Int>()
    val difBoolean = MutableLiveData<Boolean>()

    /////////////////////////////////////////////////////
    var lastIndex = 0

    val mapInt = mutableMapOf<Int, MutableLiveData<Int>>()
    val mapListInt = mutableMapOf<Int, MutableLiveData<MutableList<Int>>>()
    val mapString = mutableMapOf<Int, MutableLiveData<String>>()
    val mapBoolean = mutableMapOf<Int, MutableLiveData<Boolean>>()
    val mapGoal = mutableMapOf<Int, MutableLiveData<MutableList<Goal>>>()
    val mapGoalMap = mutableMapOf<Int, MutableLiveData<MutableMap<Int, Goal>>>()
    val mapMod = mutableMapOf<Int, MutableLiveData<MutableList<Mod>>>()

    val map =
        mutableMapOf<Int, MutableMap<Int, MutableMap<Int, String>>>()// где первое число - ключ фрагмента если несколько роллов,
    // 2 число - ключ каждого ролла, 3 - ключ виджет, строка - указание - в каком мапе искать
    // первые числа, 0 - fightTwo, 1 - fightThree, 2 -

    fun createId(): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        return newId
    }

    /////////////////////////////////////////////
    var luckyOrErudit: Boolean = true
    var usingLuckyPoint: Int? = null
    var skill: Int? = null
    var erudit: Int? = null

    var attack: EffectWeapon? = null

    init {
        m1d10.value = 1
        dif.value = 0
        allGoals.value = mutableListOf<Goal>()
        chosenGoals.value = mutableListOf<Goal>()
        critical.value = 0
        boolCritical.value = false
        difBoolean.value = false
    }

    fun clearVM (){
        onCleared()
    }
}