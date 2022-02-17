package com.example.mvvmdemo.taobao

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmdemo.base.LoadState
import com.example.mvvmdemo.domain.MapData
import kotlinx.coroutines.launch

class OnSellViewModel : ViewModel() {

    val contentList = MutableLiveData<MutableList<MapData>>()

    val loadState = MutableLiveData<LoadState>()

    companion object {
        //默认第一页
        const val DEFAULT_PAGE = 1
    }

    private val onSellRepository by lazy {
        OnSellRepository()
    }

    //当前页
    private var mCurrentPage = DEFAULT_PAGE;

    private var isLoadMore = false

    //加载更多内容
    fun loadMore() {
        isLoadMore = true
        loadState.value = LoadState.LOAD_MORE_LOADING
//        println("load more...")
        mCurrentPage++;
        this.listContentByPage(mCurrentPage)
    }

    //加载首页内容
    fun loadContent() {
        isLoadMore = false
        loadState.value = LoadState.LOADING
        this.listContentByPage(mCurrentPage)
    }

    private fun listContentByPage(page: Int) {
        viewModelScope.launch {
            try {
                val onSellList = onSellRepository.getOnSellList(page)
                val oldValue = contentList.value ?: mutableListOf()
                //println("result size == " + onSellList.tbk_dg_optimus_material_response.result_list.map_data.size)
                oldValue.addAll(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                contentList.value = oldValue
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.value = if (isLoadMore) LoadState.LOAD_MORE_EMPTY else LoadState.EMPTY
                } else {
                    loadState.value = LoadState.SUCCESS
                }
            } catch (e: Exception) {
                mCurrentPage--
                e.printStackTrace()
                if (e is NullPointerException) {

                    //没有更多内容的时候,会出现空指针
                    loadState.value = LoadState.LOAD_MORE_EMPTY
                } else {
                    loadState.value = if (isLoadMore) LoadState.LOAD_MORE_ERROR else LoadState.ERROR
                }
            }
        }
    }

}