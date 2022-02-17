package com.example.mvvmdemo.musicsList

import com.example.mvvmdemo.base.BaseFragment

class MusicListFragment:BaseFragment() {
    private val musicPresenter by lazy {
        MusicPresenter(this)
    }
}