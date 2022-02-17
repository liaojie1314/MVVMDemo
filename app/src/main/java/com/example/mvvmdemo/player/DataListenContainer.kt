package com.example.mvvmdemo.player

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.mvvmdemo.App

/**
 * 数据容器
 * 监听数据变化
 */
class DataListenContainer<T> {
    private val blocks = arrayListOf<(T?) -> Unit>()
    private val viewLifecycleProviders = HashMap<(T?) -> Unit, Lifecycle>()
    var value: T? = null
        //当数据变化时 通知更新
        set(value: T?) {
            field = value
            //判断当前线程是否为主线程
            //是则直接调用
            //否则先切换
            if (Looper.getMainLooper().thread === Thread.currentThread()) {
                //判断对应view的生命周期是什么
                blocks.forEach {
                    dispatchValue(it, value)
                }
            } else {
                App.handler.post {
                    blocks.forEach { dispatchValue(it, value) }
                }
            }

        }

    private fun dispatchValue(it: (T?) -> Unit, value: T?) {
        val lifecycle: Lifecycle? = viewLifecycleProviders[it]
        if (lifecycle != null &&
            lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        ) {
            println("更新UI...")
            it.invoke(value)
        } else {
            println("UI不可见,不进行更新...")
        }
        it.invoke(value)
    }

    /**
     * 有可能有多个view进行监听
     * 所以owner - block
     */
    fun addListener(owner: LifecycleOwner, valueObserver: (T?) -> Unit) {
        val lifecycle: Lifecycle = owner.lifecycle
        viewLifecycleProviders[valueObserver] = lifecycle
        //当view destroy时 要从集合中删除
        val observerWrapper = ValueObserverWrapper(valueObserver)
        lifecycle.addObserver(observerWrapper)
        if (!blocks.contains(valueObserver)) {
            blocks.add(valueObserver)
        }
    }

    inner class ValueObserverWrapper(private val valueObserver: (T?) -> Unit) :
        LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun removeValueObserver() {
            println("removeValueObserver...")
            //当监听到当前view 生命周期为 Destroy的时候 就把LifecycleProvider从集合中删除
            viewLifecycleProviders.remove(valueObserver)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop(owner: LifecycleOwner) {
            println("onStop:owner ==> $owner")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
            println("onAny:owner ==> $owner")
            println("onAny:event ==> $event")
        }
    }
}