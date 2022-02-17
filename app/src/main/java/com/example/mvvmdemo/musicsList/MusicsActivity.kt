package com.example.mvvmdemo.musicsList

import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.player.domain.Music
import kotlinx.android.synthetic.main.activity_musics.*

class MusicsActivity : BaseActivity() {
    private lateinit var mForeverObserver: ForeverObserver

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musics)
        initDataListener()
        initViewListener()
    }

    inner class ForeverObserver : Observer<List<Music>> {
        override fun onChanged(result: List<Music>?) {
            println("forever observer data change --- ${result?.size}")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        musicPresenter.liveMusicList.removeObserver(mForeverObserver)
    }


    /**
     * 监听数据变化
     */
    private fun initDataListener() {

        mForeverObserver = ForeverObserver()
        musicPresenter.liveMusicList.observeForever(mForeverObserver)

        musicPresenter.liveMusicList.observe(this, Observer {
            //TODO:更新UI
            println("live data 里的音乐列表数据更新了...${it.size}")
        })
        musicPresenter.musicList.addListener(this) {
            println(Thread.currentThread().name)
            //数据变化
            println("加载状态 ---> ${it?.size}")
            musicCountText?.text = "加载到了 ---> ${it?.size}条数据"
        }
        musicPresenter.loadState.addListener(this) {
            println("加载状态 ---> $it")
        }
    }

    private fun initViewListener() {
        getMusicsBtn.setOnClickListener {
            musicPresenter.getMusic()
        }
    }
}