package com.water.song.template.common.kv

import com.water.song.template.common.WLog
import io.fastkv.FastKV
import java.lang.Exception

/**
 * @author Water-Song
 */
internal class KVLogger: FastKV.Logger {
    companion object {
        const val LOG_TAG = "KVLogger"
    }
    override fun i(name: String?, message: String?) {
        message ?: return
        WLog.i(name ?: LOG_TAG, message)
    }

    override fun w(name: String?, e: Exception?) {
        e ?: return
        WLog.w(name ?: LOG_TAG, e)
    }

    override fun e(name: String?, e: Exception?) {
        e ?: return
        WLog.e(name ?: LOG_TAG, e)
    }
}