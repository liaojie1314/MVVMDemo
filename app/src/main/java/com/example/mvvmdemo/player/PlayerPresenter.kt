package com.example.mvvmdemo.player

import com.example.mvvmdemo.lifecycle.AbsLifecycle
import com.example.mvvmdemo.lifecycle.ILifeCycle
import com.example.mvvmdemo.lifecycle.LifeState
import com.example.mvvmdemo.player.domain.Music

/**
 * 播放音乐
 * 暂停音乐
 * 上一首
 * 下一首
 * ========
 * 播放状态:
 * - 通知UI改变为播放状态
 * - 通知UI标题变化
 * 上一首、下一首
 * - 通知UI标题变化
 * - 通知UI歌曲封面变化
 * 暂停音乐
 * - 通知UI改变为暂停
 *相关数据
 * 当前播放的歌曲
 * 当前播放的状态
 */
class PlayerPresenter private constructor() :AbsLifecycle(){
    private val playerModel by lazy {
        PlayerModel()
    }
    private val player by lazy {
        MusicPlayer()
    }
    var currentMusic = DataListenContainer<Music>()

    var livePlayerState = LivePlayerState.instance

    var currentPlayState = DataListenContainer<PlayState>()

    companion object {
        val instance by lazy {
            PlayerPresenter()
        }
    }

    enum class PlayState {
        NONE, PLAYING, PAUSE, LOADING
    }
    /**
     * 根据状态 播放或者暂停播放
     */
    fun doPlayOrPause() {
        if (currentMusic.value == null) {
            //去获取一首歌曲
            currentMusic.value = playerModel.getMusicById("xxx")
        }
        player.play(currentMusic.value)
        if (currentPlayState.value != PlayState.PLAYING) {
            //开始播放音乐
            currentPlayState.value = PlayState.PLAYING
            livePlayerState.postValue(PlayState.PLAYING)
        } else{
            //暂停
            currentPlayState.value = PlayState.PAUSE
            livePlayerState.postValue(PlayState.PAUSE)
        }
    }
    /**
     * 播放下一首歌曲
     */
    fun playNext() {
        currentMusic.value = playerModel.getMusicById("下一首")
        //TODO:播放下一首
        //1.拿到下一首歌曲 --> 变更UI 包括标题和封面
        //2.设置给播放器
        //3.等待播放回调通知
        currentPlayState.value = PlayState.PLAYING
    }

    /**
     * 播放上一首歌曲
     */
    fun playPre() {
        currentMusic.value = playerModel.getMusicById("上一首")
        currentPlayState.value = PlayState.PLAYING
    }

    override fun onCreate() {

    }

    override fun onStart() {
        //监听网络状态变化
        println("监听网络状态变化...")
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {
        println("停止监听网络状态...")
    }

    override fun onDestroy() {

    }

    override fun onViewLifeStateChange(state: LifeState) {

    }

}