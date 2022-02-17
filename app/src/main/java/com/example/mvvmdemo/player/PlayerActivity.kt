package com.example.mvvmdemo.player

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.musicsList.MusicPresenter
import kotlinx.android.synthetic.main.activity_player.*
import java.util.*

class PlayerActivity : BaseActivity() {
    private val musicPresenter by lazy {
        MusicPresenter(this)
    }
    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    fun toFlowPage(view:View){
        startActivity(Intent(this,FlowPlayerControllerActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        initListener()
        initDataListener()
    }

    private fun initListener() {
        playOrPauseBtn.setOnClickListener {
            playerPresenter.doPlayOrPause()
        }
        playNextBtn.setOnClickListener {
            playerPresenter.playNext()
        }
        playPreBtn.setOnClickListener {
            playerPresenter.playPre()
        }
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
            println("播放器界面...live data ---> 当前状态 ---> $t")
        }

    }

    /**
     * 对数据进行监听
     */
    private fun initDataListener() {

        LivePlayerState.instance.observeForever(livePlayerObserver)

        playerPresenter.currentMusic.addListener(this) {
            //音乐内容变化 更新UI
            songTitle.text = it?.name
            println("封面改变了${it?.cover}")
        }
        playerPresenter.currentPlayState.addListener(this) {
            when (it) {
                PlayerPresenter.PlayState.PAUSE -> {
                    playOrPauseBtn.text = "播放"
                }
                PlayerPresenter.PlayState.PLAYING -> {
                    playOrPauseBtn.text = "暂停"
                }
            }
        }
    }
}