package com.water.song.template.common.executor

import java.util.concurrent.Executor

/**
 * @author Water-Song
 */
@Suppress("unused")
class WsExecutor private constructor() {
    companion object {
        @JvmStatic
        fun realExecutor(): Executor {
            return DefaultExecutor.getInstance()
        }

        @JvmStatic
        fun execute(runnable: Runnable) {
            DefaultExecutor.getInstance().execute(runnable)
        }

        @JvmStatic
        fun cancel(runnable: Runnable): Boolean {
            return DefaultExecutor.getInstance().queue.remove(runnable)
        }

        @JvmStatic
        fun shutdown() {
            DefaultExecutor.getInstance().shutdown()
        }

        @JvmStatic
        fun shutdownNow(): List<Runnable> {
            return DefaultExecutor.getInstance().shutdownNow()
        }

        @JvmStatic
        fun isShutdown(): Boolean {
            return DefaultExecutor.getInstance().isShutdown
        }

        @JvmStatic
        fun isTerminated(): Boolean {
            return DefaultExecutor.getInstance().isTerminated
        }

        @JvmStatic
        fun isTerminating(): Boolean {
            return DefaultExecutor.getInstance().isTerminating
        }
    }
}