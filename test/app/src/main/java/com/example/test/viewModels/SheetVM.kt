package com.example.test.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SheetVM:ViewModel() {

    val map = MutableLiveData<MutableList<MutableLiveData<MutableList<MutableLiveData<String>>>>>(mutableListOf())

    var rowCount = MutableLiveData<Int>(1)

    fun calcRowCount(){
        rowCount.value = map.value?.get(0)?.value?.size
    }

    fun resetCell(rowPos:Int, cellPos:Int, text:String){
        map.value?.get(rowPos)?.value?.get(cellPos)?.value = text
    }

    fun addColumn():Int?{
        map.value?.add(MutableLiveData(mutableListOf()))
        return map.value?.size?.minus(1)
    }

    fun addCell(rowPos: Int, text:String):Int?{
        map.value?.get(rowPos)?.value?.add(MutableLiveData(text))
        return map.value?.get(rowPos)?.value?.size?.minus(1)
    }

    init{
        for (x in 0..5) {
            val a = addColumn()!!
            for (y in 0..5) {
                addCell(a, "$x $y")
            }
        }
    }

    fun deleteColumn(rowPos: Int){
        map.value?.removeAt(rowPos)
    }

    fun deleteRow(rowPos: Int){
        for (i in map.value!!){
            i.value?.removeAt(rowPos)
        }
        //rowCount.value = rowCount.value?.minus(1)
    }
}