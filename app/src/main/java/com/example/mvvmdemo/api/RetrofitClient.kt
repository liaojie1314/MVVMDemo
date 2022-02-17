package com.example.mvvmdemo.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 创建Retrofit已经提供API Service
 */
object RetrofitClient {
    //设置超时时间
    val okHttpClient=OkHttpClient.Builder()
        .callTimeout(30,TimeUnit.SECONDS)
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}