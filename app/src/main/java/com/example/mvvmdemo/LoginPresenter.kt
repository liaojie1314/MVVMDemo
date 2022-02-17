package com.example.mvvmdemo

import android.text.TextUtils
import com.example.mvvmdemo.UserModel.Companion.STATE_LOGIN_FAILED
import com.example.mvvmdemo.UserModel.Companion.STATE_LOGIN_LOADING
import com.example.mvvmdemo.UserModel.Companion.STATE_LOGIN_SUCCESS
import com.example.mvvmdemo.lifecycle.AbsLifecycle
import com.example.mvvmdemo.lifecycle.ILifecycleOwner
import com.example.mvvmdemo.lifecycle.LifeState

class LoginPresenter(owner: ILifecycleOwner) : AbsLifecycle() {

    init {
        owner.getLifecycleProvider().addLifeListener(this)
    }

    private val userModel by lazy {
        UserModel()
    }

    fun checkUserNameState(account: String, callback: OnCheckUserNameStateResultCallback) {
        //检查
        userModel.checkUserState(account) {
            when (it) {
                0 -> {
                    callback.onExist()
                }
                1 -> {
                    callback.onNotExist()
                }
            }
        }
    }

    interface OnCheckUserNameStateResultCallback {
        fun onNotExist()
        fun onExist()
    }

    fun doLogin(userName: String, password: String, callback: OnDoLoginStateChange) {
        if (TextUtils.isEmpty(userName)) {
            //TODO:提示账号有问题
            callback.onAccountFormatError()
            return
        }
        if (TextUtils.isEmpty(password)) {
            //TODO:提示密码有问题
            callback.onPasswordEmpty()
            return
        }
        userModel.doLogin(userName, password) {
            when (it) {
                STATE_LOGIN_LOADING -> {
                    callback.onLoading()
                }
                STATE_LOGIN_FAILED -> {
                    callback.onLoginFailed()
                }
                STATE_LOGIN_SUCCESS -> {
                    callback.onLoginSuccess()
                }
            }
        }
    }

    interface OnDoLoginStateChange {
        fun onAccountFormatError()
        fun onPasswordEmpty()
        fun onLoading()
        fun onLoginSuccess()
        fun onLoginFailed()
    }

    override fun onViewLifeStateChange(state: LifeState) {
        println("current state === > $state")
    }
}