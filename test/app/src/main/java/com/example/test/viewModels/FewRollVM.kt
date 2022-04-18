package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FewRollVM : ViewModel() {
    val list = MutableLiveData<MutableList<Int>>()

    init {
        list.value = mutableListOf()
    }

    fun add(id: Int) {
        list.value?.add(id)
    }

    fun delete(id: Int) {
        list.value?.remove(id)
    }

    fun getIndex(element: Int): Int {
        return list.value?.indexOf(element) ?: 0
    }

    fun getElement(index: Int): Int {
        return list.value?.get(index) ?: 0
    }
}