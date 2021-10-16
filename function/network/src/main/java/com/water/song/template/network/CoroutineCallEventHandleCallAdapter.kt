package com.water.song.template.network

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class CoroutineCallEventHandleCallAdapter<R> internal constructor(
    private val responseType: Type,
    private val enableCancel: Boolean
) : CallAdapter<R, CoroutineLifecycleCall<R>> {
    override fun adapt(call: Call<R>): CoroutineLifecycleCall<R> {
        return CoroutineLifecycleCallImpl(call, enableCancel)
    }

    override fun responseType(): Type {
        return responseType
    }
}