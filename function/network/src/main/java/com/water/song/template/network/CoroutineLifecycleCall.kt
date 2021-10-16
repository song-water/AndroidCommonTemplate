package com.water.song.template.network

interface CoroutineLifecycleCall<T> {
    fun cancel()
    fun enqueue(callback: CoroutineLifecycleCallback<T>)
    fun clone(): CoroutineLifecycleCall<T>
    fun isCanceled(): Boolean
    fun enableCancel(): Boolean
}