package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.widgets.Mod

class SkillTestVM:ViewModel() {
    var difficult = MutableLiveData<Int>()
    val modification = MutableLiveData<MutableList<Mod>>()
    val m1d10 = MutableLiveData<Int>()
    val critical = MutableLiveData<Int>()
    val boolCritical = MutableLiveData<Boolean>()

    init {
        m1d10.value = 1
        difficult.value = 0
        modification.value = mutableListOf<Mod>()
        critical.value = 0
        boolCritical.value = false
    }
}