package com.example.mvvmdemo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.musicsList.MusicPresenter
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity(),
    LoginPresenter.OnCheckUserNameStateResultCallback,
    LoginPresenter.OnDoLoginStateChange {
    private val musicPresenter by lazy {
        MusicPresenter(this)
    }
    private val loginPresenter by lazy {
        LoginPresenter(this)
    }
    private var isUserNameCanBeUse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener() {
        loginBtn.setOnClickListener {
            //进行登录
            toLogin()
        }
        //监听内容变化
        accountInputBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //检查当前账号是否注册
                loginPresenter.checkUserNameState(s.toString(), this@LoginActivity)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun toLogin() {
        //登录
        //检查账号和密码是否正确
        val account: String = accountInputBox.text.toString()
        val password: String = passwordInputBox.text.toString()
        if (!isUserNameCanBeUse) {
            //提示当前账号已经被注册
            return
        }
        //异步操作 耗时
        loginPresenter.doLogin(
            account,
            password,
            this
        )
    }

    override fun onAccountFormatError() {
        loginTipsText?.text = "账号为空"
    }

    override fun onPasswordEmpty() {
        loginTipsText?.text = "密码为空"
    }

    override fun onLoading() {
        loginTipsText?.text = "登录中"
    }

    override fun onLoginSuccess() {
        loginTipsText?.text = "登录成功"
    }

    override fun onLoginFailed() {
        loginTipsText?.text = "登录失败"
    }

    override fun onNotExist() {
        this.isUserNameCanBeUse = true
    }

    override fun onExist() {
        this.isUserNameCanBeUse = false
        loginTipsText?.text = "该用户已经注册"
    }
}