package com.example.ch3_maskinfokt.repository

import com.example.ch3_maskinfokt.model.StoreInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MaskService {
    //우리는 샘플 데이터이기 때문에 위도 경도에 따른 약국 우선순위가 나오지 않는다. -> 코드만 수정함.
    @GET("junsuk5/bb7485d5f70974deee920b8f0cd1e2f0/raw/063f64d9b343120c2cb01a6555cf9b38761b1d94/sample.json/?m=5000")
    suspend fun fetchStoreInfo(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): StoreInfo

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com"
    }
}