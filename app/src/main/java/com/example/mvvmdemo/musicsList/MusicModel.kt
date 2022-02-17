package com.example.mvvmdemo.musicsList

import com.example.mvvmdemo.player.domain.Music
import kotlin.concurrent.thread

class MusicModel {
    fun loadMusicByPage(page: Int, size: Int, callback: OnMusicLoadResult) {
        val result: ArrayList<Music> = arrayListOf<Music>()
        thread {
            Thread.sleep(2000)
            for (i in (0 until size)) {
                result.add(
                    Music(
                        "音乐名称 $i",
                        "cover 封面 $i",
                        "url ==> $i"
                    )
                )
            }
            //数据处理完成
            //通知UI更新
            callback.onSuccess(result)
        }
    }

    interface OnMusicLoadResult {
        fun onSuccess(result: List<Music>)
        fun onError(msg: String, code: Int)
    }

}