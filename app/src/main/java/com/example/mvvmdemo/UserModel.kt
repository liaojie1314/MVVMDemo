package com.example.mvvmdemo

import java.util.*

class UserModel {
    companion object{
        const val STATE_LOGIN_LOADING = 0
        const val STATE_LOGIN_SUCCESS = 1
        const val STATE_LOGIN_FAILED = 2
    }
    private val api by lazy {
        API()
    }
    private val random = Random()

    /**
     * 进行登录
     */
    fun doLogin(account: String, password: String,block:(Int)->Unit) {
        block.invoke(STATE_LOGIN_LOADING)
        //开始调用登录api
        //有结果 此为耗时操作
        //向服务器提交数据 使用非主线程去操作
        //异步操作 通知UI时要切换为主线程
        //因为UI只能在主线程更新
        val randomValue: Int = random.nextInt(2)
        if(randomValue==0)
        {
            block.invoke(STATE_LOGIN_SUCCESS)
        }else{
            block.invoke(STATE_LOGIN_FAILED)
        }
    }
    fun checkUserState(account: String,block: (Int)->Unit){
        //0表示账号已经注册
        //1表示账号未注册
        block.invoke(random.nextInt(2))
    }
}