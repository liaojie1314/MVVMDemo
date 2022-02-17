package com.example.mvvmdemo.player

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_flow_player.*

class FlowPlayerControllerActivity : BaseActivity() {
    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_player)
        initListener()
        initDataListener()
    }

    private val livePlayerObserver by lazy {
        LivePlayerStateObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        LivePlayerState.instance.removeObserver(livePlayerObserver)
    }

    class LivePlayerStateObserver() : Observer<PlayerPresenter.PlayState> {
        override fun onChanged(t: PlayerPresenter.PlayState?) {
            println("悬浮界面...live data ---> 当前状态 ---> $t")
        }

    }

    private fun initDataListener() {
        LivePlayerState.instance.observeForever(livePlayerObserver)
        playerPresenter.currentPlayState.addListener(this) {
            playOrPauseBtn.text = if (it === PlayerPresenter.PlayState.PLAYING) {
                "暂停"
            } else {
                "播放"
            }
        }
    }

    private fun initListener() {
        playOrPauseBtn.setOnClickListener {
            playerPresenter.doPlayOrPause()

        }
    }
}