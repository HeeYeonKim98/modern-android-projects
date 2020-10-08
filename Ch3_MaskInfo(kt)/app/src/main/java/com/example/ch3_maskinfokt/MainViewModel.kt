package com.example.ch3_maskinfokt

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ch3_maskinfokt.model.Store
import com.example.ch3_maskinfokt.repository.MaskService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel(){
    val itemLiveData = MutableLiveData<List<Store>>()
    val loadingLiveData = MutableLiveData<List<Store>>()

    //나중에 초기화 해도 되는 변수를 사용할 때 lateinit var를 사용. 자바에서는  MaskService service; 로 선언하는 것과 같음.
//    lateinit var service: MaskService
    private var service: MaskService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(MaskService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        service = retrofit.create(MaskService::class.java)
    }
}