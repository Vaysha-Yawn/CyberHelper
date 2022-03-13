package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.widgets.Mod

class SkillTestVM:ViewModel() {

    val modification = MutableLiveData<MutableList<Mod>>() // mod.value = -1 , оно сохраняется с индексом на 1 больше, чтобы оставить при создании начальную фразу
    val m1d10 = MutableLiveData<Int>()
    val critical = MutableLiveData<Int>()
    val boolCritical = MutableLiveData<Boolean>()
    val deletedIdByMod = mutableListOf<Int>()
    val dif = MutableLiveData<Int>()
    val difBoolean = MutableLiveData<Boolean>()

    var luckyOrErudit:Boolean? = null
    var usingLuckyPoint:Int? = null
    var skill :Int? = null

    init {
        m1d10.value = 1
        dif.value = 0
        modification.value = mutableListOf<Mod>()
        critical.value = 0
        boolCritical.value = false
        difBoolean.value = false
    }
}