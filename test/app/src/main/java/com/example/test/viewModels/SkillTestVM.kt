package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data_base.EffectWeapon
import com.example.test.widgets.Goal
import com.example.test.widgets.Mod

class SkillTestVM:ViewModel() {

    var title = ""

    val modification =
        MutableLiveData<MutableList<Mod>>() // mod.value = -1 , оно сохраняется с индексом на 1 больше, чтобы оставить при создании начальную фразу
    val allGoals = MutableLiveData<MutableList<Goal>>()
    val chosenGoals = MutableLiveData<MutableList<Goal>>()
    val m1d10 = MutableLiveData<Int>()
    val critical = MutableLiveData<Int>()
    val boolCritical = MutableLiveData<Boolean>()
    val deletedIdByMod = mutableListOf<Int>()
    val deletedIdByGoal = mutableListOf<Int>()
    val dif = MutableLiveData<Int>()
    val difBoolean = MutableLiveData<Boolean>()

    /////////////////////////////////////////////////////
    var lastIndex = 0

    val mapInt = mutableMapOf<Int, MutableLiveData<Int>>()
    val mapString = mutableMapOf<Int, MutableLiveData<String>>()
    val mapBoolean = mutableMapOf<Int, MutableLiveData<Boolean>>()
    val mapGoal = mutableMapOf<Int, MutableLiveData<Goal>>()
    val mapMod = mutableMapOf<Int, MutableLiveData<MutableList<Mod>>>()

    fun addToMapInt(value: Int): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        mapInt[newId] = MutableLiveData<Int>()
        mapInt[newId]?.value = value
        return newId
    }

    fun addToMapString(value: String): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        mapString[newId] = MutableLiveData<String>()
        mapString[newId]?.value = value
        return newId
    }

    fun addToMapBoolean(value: Boolean): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        mapBoolean[newId] = MutableLiveData<Boolean>()
        mapBoolean[newId]?.value = value
        return newId
    }

    fun addToMapGoal(value: Goal): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        mapGoal[newId] = MutableLiveData<Goal>()
        mapGoal[newId]?.value = value
        return newId
    }

    fun addToMapMod(): Int {
        val newId = lastIndex + 1
        lastIndex = newId
        mapMod[newId] = MutableLiveData<MutableList<Mod>>()
        return newId
    }

    fun updateToMapInt(key: Int, value: Int) {
        mapInt[key]?.value = value
    }

    fun updateToMapString(key: Int, value: String) {
        mapString[key]?.value = value
    }

    fun updateToMapBoolean(key: Int, value: Boolean) {
        mapBoolean[key]?.value = value
    }

    fun updateToMapGoal(key: Int, value: Goal) {
        mapGoal[key]?.value = value
    }

    fun updateAddToMapMod(key: Int, value: Mod) {
        mapMod[key]?.value?.add(value)
    }

    fun updateDeleteToMapMod(key: Int, position: Int) {
        mapMod[key]?.value?.removeAt(position)
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
        modification.value = mutableListOf<Mod>()
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