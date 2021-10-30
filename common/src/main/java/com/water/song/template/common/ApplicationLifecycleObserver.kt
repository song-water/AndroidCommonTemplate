package com.water.song.template.common

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author Water-Song
 */
class ApplicationLifecycleObserver : LifecycleObserver {
    companion object {
        private const val TAG: String = "AppLifecycleObserver"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        WLog.i(TAG, "ON_CREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {//Foreground
        WLog.i(TAG, "ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {//Foreground
        WLog.i(TAG, "ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {//Background
        WLog.i(TAG, "ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {//Background
        WLog.i(TAG, "ON_STOP")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestory() {
        WLog.i(TAG, "ON_DESTROY")
    }
}