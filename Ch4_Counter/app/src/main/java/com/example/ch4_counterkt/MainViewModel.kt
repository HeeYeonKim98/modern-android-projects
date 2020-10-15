package com.example.ch4_counterkt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MainViewModel(private val handle: SavedStateHandle) :ViewModel(){
    private var count = handle.get<Int>("count") ?:0 //null이면 0
        //setter override
    set(value) {
            countLiveData.value = value
            field = value //backign field -> 반영을 해야함, 이 값을 확정 짓겠다! 재 셋팅을 해야 count 본연의 값이 된다.
            handle.set("count", value)
        }

    val countLiveData = MutableLiveData<Int>()

    fun increaseCount(){
        count++
    }

    fun decreaseCount(){
        count--
    }
}