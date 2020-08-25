package com.first.springer.common

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
class MyApplication : Application(), LifecycleObserver {


 lateinit  var  stateMonitor: ConnectionStateMonitor
    override fun onCreate() {
        super.onCreate()
        stateMonitor = ConnectionStateMonitor()
        stateMonitor.enable(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        wasInBackground = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        // app moved to background
        wasInBackground = false
    }

    companion object {
        @JvmField
        var wasInBackground = false
        @JvmField
        var available = false
        @JvmField
        var swipetoast = false
    }
}