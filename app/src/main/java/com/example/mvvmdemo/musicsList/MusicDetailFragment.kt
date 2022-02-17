package com.example.mvvmdemo.musicsList
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvmdemo.base.BaseFragment
import com.example.mvvmdemo.lifecycle.LifeState
import com.example.mvvmdemo.lifecycle.LifecycleProvider

class MusicDetailFragment: BaseFragment() {
    private val musicPresenter by lazy {
        MusicPresenter(this)
    }
}