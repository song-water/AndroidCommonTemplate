package com.water.song.template.common.kv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

/**
 * @author Water-Song
 * copy from fastkv demo
 */
internal class ChannelExecutorService(capacity: Int) : AbstractExecutorService() {
    private val channel = Channel<Any>(capacity)

    /**
     * [command] will be execute by [Dispatchers.IO] finally.
     */
    override fun execute(command: Runnable) {
        channel.run {
            command.run()
        }
    }

    @ExperimentalCoroutinesApi
    fun isEmpty(): Boolean {
        return channel.isEmpty || channel.isClosedForReceive
    }

    /**
     * Note: calling [execute] after shutdown will throw [ClosedSendChannelException]
     */
    override fun shutdown() {
        channel.close()
    }

    /**
     * The method was not completely implement, DON'T USE THIS METHOD.
     */
    override fun shutdownNow(): MutableList<Runnable> {
        shutdown()
        return mutableListOf()
    }

    @ExperimentalCoroutinesApi
    override fun isShutdown(): Boolean {
        return channel.isClosedForSend
    }

    @ExperimentalCoroutinesApi
    override fun isTerminated(): Boolean {
        return channel.isClosedForReceive
    }

    override fun awaitTermination(timeout: Long, unit: TimeUnit): Boolean {
        var millis = unit.toMillis(timeout)
        while (!isTerminated && millis > 0) {
            try {
                Thread.sleep(200L)
                millis -= 200L
            } catch (ignore: Exception) {
            }
        }
        return isTerminated
    }
}