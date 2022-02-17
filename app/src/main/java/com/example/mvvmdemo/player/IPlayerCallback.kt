package com.example.mvvmdemo.player

interface IPlayerCallback {
    fun onTitleChange(title:String)

    fun onProgressChange(current:String)

    fun onPlaying()

    fun onPlayerPause()

    fun onCoverChange(cover:String)
}