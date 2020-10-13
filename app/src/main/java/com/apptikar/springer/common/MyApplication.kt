package com.apptikar.springer.common
import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application(), LifecycleObserver {


 private  lateinit  var  stateMonitor: ConnectionStateMonitor
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()
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
        var swipeToast = false
        @JvmField
     var mAuth: FirebaseAuth? = null
    }
}