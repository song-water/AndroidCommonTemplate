package com.water.song.template.common

import android.app.Application
import android.content.Context
import androidx.annotation.CallSuper

/**
 * @author Water-Song
 */
open class WsBaseApplication : Application() {
    companion object {
        const val TAG = "WsBaseApplication"
    }

    @CallSuper
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    @CallSuper
    override fun onCreate() {
        super.onCreate()
    }
}