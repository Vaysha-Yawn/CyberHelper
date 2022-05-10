package com.example.test.viewModels

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SheetVM:ViewModel() {

    val map = MutableLiveData<MutableList<MutableLiveData<MutableList<MutableLiveData<String>>>>>(mutableListOf())

    var columnCount = MutableLiveData<Int>()

    init{
        columnCount.value = 1
    }

    fun resetCell(rowPos:Int, cellPos:Int, text:String){
        map.value?.get(rowPos)?.value?.get(cellPos)?.value = text
    }


    fun addRow():Int?{
        map.value?.add(MutableLiveData(mutableListOf()))
        return map.value?.size?.minus(1)
    }

    fun addCell(rowPos: Int, text:String):Int?{
        map.value?.get(rowPos)?.value?.add(MutableLiveData(text))
        return map.value?.get(rowPos)?.value?.size?.minus(1)
    }

    fun deleteRow(rowPos: Int){
        map.value?.removeAt(rowPos)
    }
}