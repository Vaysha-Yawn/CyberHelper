package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FewRollVM():ViewModel() {
    val list = MutableLiveData<MutableList<Int>>()
    init {
        list.value = mutableListOf(1)
    }
}