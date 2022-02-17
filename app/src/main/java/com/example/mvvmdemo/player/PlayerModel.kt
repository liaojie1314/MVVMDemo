package com.example.mvvmdemo.player

import com.example.mvvmdemo.player.domain.Music

class PlayerModel {
    fun getMusicById(id: String): Music {
        return Music(
            "歌曲名:$id",
            "https://xxx",
            "https://xxx"
        )
    }

}