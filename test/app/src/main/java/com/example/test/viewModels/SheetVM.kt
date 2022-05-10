package com.example.test.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SheetVM:ViewModel() {

    val map = MutableLiveData<MutableList<MutableList<String>>>()

    var columnCount = MutableLiveData<Int>()

    init{
        columnCount.value = 1
        map.value = mutableListOf(mutableListOf("00","01", "02"), mutableListOf("10","11", "12"), mutableListOf("20","21", "22"))
    }

    fun resetCell(rowPos:Int, cellPos:Int, text:String){
        map.value?.get(rowPos)?.set(cellPos, text)
    }

    fun addRow(){

    }

    fun addColumn(){

    }
}