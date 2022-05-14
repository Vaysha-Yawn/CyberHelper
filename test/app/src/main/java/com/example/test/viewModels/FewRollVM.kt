package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.widgets.Roll

class FewRollVM : ViewModel() {

    val fragments = MutableLiveData(mutableListOf<Roll>())

    val list = MutableLiveData<MutableList<Int>>()

    init {
        list.value = mutableListOf()
    }

    fun add(id: Int, fragment: Roll) {
        list.value?.add(id)
        fragments.value!!.add(fragment)
    }

    fun delete(id: Int, position: Int) {
        list.value?.remove(id)
        fragments.value!!.removeAt(position)
    }

    fun getIndex(element: Int): Int {
        return list.value?.indexOf(element) ?: 0
    }

    fun getElement(index: Int): Int {
        return list.value?.get(index) ?: 0
    }
}