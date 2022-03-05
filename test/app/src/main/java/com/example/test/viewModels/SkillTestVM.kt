package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SkillTestVM:ViewModel() {
    val difficult = MutableLiveData<Int>()
    val modification = MutableLiveData<Int>()
    val m1d10 = MutableLiveData<Int>()
    init {
        m1d10.value = 1
        difficult.value = 0
        modification.value = 0
    }
}