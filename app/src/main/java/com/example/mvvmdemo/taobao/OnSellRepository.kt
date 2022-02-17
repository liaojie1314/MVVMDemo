package com.example.mvvmdemo.taobao

import com.example.mvvmdemo.api.RetrofitClient

class OnSellRepository {

    suspend fun getOnSellList(page: Int) =
        RetrofitClient.apiService.getOnSellList(page).apiData()
}