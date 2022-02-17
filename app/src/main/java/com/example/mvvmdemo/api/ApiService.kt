package com.example.mvvmdemo.api

import com.example.mvvmdemo.domain.OnSellData
import com.example.mvvmdemo.domain.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/shop/"
    }

    //支持挂起
    @GET("onSell/{page}")
    suspend fun getOnSellList(@Path("page") page: Int): ResultData<OnSellData>
}