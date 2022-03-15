package com.water.song.template.common.executor

import android.util.Log
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Water-Song
 */
internal class NamedThreadFactory(
    private val factoryName: String
) : ThreadFactory {

    private val threadCount = AtomicInteger(1)

    companion object {
        const val TAG = "NamedThreadFactory"
        const val DEFAULT_PRIORITY = android.os.Process.THREAD_PRIORITY_BACKGROUND +
                android.os.Process.THREAD_PRIORITY_MORE_FAVORABLE
    }

    override fun newThread(runnable: Runnable): Thread {
        val name = "factoryName_$runnable#${threadCount.get()}"
        return object : Thread(runnable, name) {
            override fun run() {
                kotlin.runCatching {
                    android.os.Process.setThreadPriority(DEFAULT_PRIORITY)
                    super.run()
                }.onFailure {
                    Log.e(TAG, "newThread failure: $it")
                }
            }
        }
    }
}