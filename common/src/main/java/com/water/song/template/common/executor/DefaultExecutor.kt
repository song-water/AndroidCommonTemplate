package com.water.song.template.common.executor

import java.util.concurrent.*
import kotlin.math.max
import kotlin.math.min

/**
 * @author Water-Song
 */
internal class DefaultExecutor private constructor(
    corePoolSize: Int,
    maximumPoolSize: Int,
    keepAliveTime: Long,
    unit: TimeUnit?,
    workQueue: BlockingQueue<Runnable>?,
    threadFactory: ThreadFactory?,
    handler: RejectedExecutionHandler?
) : ThreadPoolExecutor(
    corePoolSize,
    maximumPoolSize,
    keepAliveTime,
    unit,
    workQueue,
    threadFactory,
    handler
) {
    companion object {
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val CORE_POOL_SIZE = max(2, min(CPU_COUNT, 4))
        private val MAX_POOL_SIZE = CPU_COUNT * 2 - 1
        private const val KEEP_ALIVE_TIME = 30L

        @JvmStatic
        private fun create(): DefaultExecutor {
            return DefaultExecutor(
                CPU_COUNT,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                LinkedBlockingDeque(),
                NamedThreadFactory("water_song_worker-pool"),
                DiscardOldestPolicy()
            )
        }

        @JvmStatic
        fun getInstance(): DefaultExecutor = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = create()
    }
}