package com.example.ch3_maskinfokt

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ch3_maskinfokt.model.Store
import com.example.ch3_maskinfokt.repository.MaskService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel @ViewModelInject constructor(
    private var service: MaskService
): ViewModel(){
    val itemLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    init {
        fetchStoreInfo()
    }

    fun fetchStoreInfo(){
        loadingLiveData.value = true

        viewModelScope.launch {
            val storeInfo = service.fetchStoreInfo(37.188078,127.043002);
            itemLiveData.value = storeInfo.stores

            loadingLiveData.value = false
        }
        //fetchStoreInfo는 suspen메서드 안에서만 수행할 수 있음

    }
}