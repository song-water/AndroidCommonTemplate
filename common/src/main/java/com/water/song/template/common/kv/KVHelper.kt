package com.water.song.template.common.kv

import io.fastkv.FastKVConfig
import java.util.concurrent.Executor

/**
 * @author Water-Song
 */
class KVHelper {
    private object Holder {
        val INSTANCE = KVHelper()
    }

    companion object {
        private var isKvHelperInitialized = false

        @JvmStatic
        fun getInstance() = Holder.INSTANCE

        @JvmStatic
        fun init() {
            if (isKvHelperInitialized) {
                return;
            }
            init(ChannelExecutorService(4))
        }

        @JvmStatic
        fun init(executor: Executor) {
            if (isKvHelperInitialized) {
                return;
            }
            isKvHelperInitialized = true
            FastKVConfig.setLogger(KVLogger())
            FastKVConfig.setExecutor(executor)
        }
    }
}