package com.water.song.template.app

import android.content.Context
import com.water.song.template.common.WLog
import com.water.song.template.common.WsBaseApplication
import com.water.song.template.common.kv.KVHelper

/**
 * @author Water-Song
 */
class WaterApplication : WsBaseApplication() {
    companion object {
        const val TAG = "WaterApplication"
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        KVHelper.init()
        WLog.i(TAG, "attachBaseContext")
    }

    override fun onCreate() {
        super.onCreate()
        WLog.i(TAG, "onCreate")
    }
}