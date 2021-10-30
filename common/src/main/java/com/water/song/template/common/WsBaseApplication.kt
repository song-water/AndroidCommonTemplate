package com.water.song.template.common

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.lifecycle.ProcessLifecycleOwner
import kotlin.properties.Delegates

/**
 * @author Water-Song
 */
open class WsBaseApplication : Application() {
    companion object {
        const val TAG = "WsBaseApplication"
        var instance: WsBaseApplication by Delegates.notNull()
            private set
    }

    @CallSuper
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ProcessLifecycleOwner.get().lifecycle.addObserver(applicationLifecycleObserver)
    }

    @CallSuper
    override fun onTerminate() {
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ProcessLifecycleOwner.get().lifecycle.removeObserver(applicationLifecycleObserver)
        super.onTerminate()
    }

    private val applicationLifecycleObserver = ApplicationLifecycleObserver()

    private val activityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            WLog.d(TAG, activity.javaClass.simpleName + " onCreated")
        }

        override fun onActivityStarted(activity: Activity) {
            WLog.d(TAG, activity.javaClass.simpleName + " onStarted")
        }

        override fun onActivityResumed(activity: Activity) {
            WLog.d(TAG, activity.javaClass.simpleName + " onResumed")
        }

        override fun onActivityPaused(activity: Activity) {
            WLog.d(TAG, activity.javaClass.simpleName + " onPaused")
        }

        override fun onActivityStopped(activity: Activity) {
            WLog.d(TAG, activity.javaClass.simpleName + " onStopped")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            WLog.d(TAG, activity.javaClass.simpleName + " onSaveInstanceState")
        }

        override fun onActivityDestroyed(activity: Activity) {
            WLog.d(TAG, activity.javaClass.simpleName + " onDestroyed")
        }
    }
}