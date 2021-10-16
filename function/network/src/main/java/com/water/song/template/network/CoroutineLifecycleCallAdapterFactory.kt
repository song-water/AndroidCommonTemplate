package com.water.song.template.network

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@Suppress("unused")
class CoroutineLifecycleCallAdapterFactory(
    private val enableCancel: Boolean = true
) : CallAdapter.Factory() {

    fun getEnableCancel(): Boolean {
        return enableCancel
    }

    override fun get(
        returnType: Type?,
        annotations: Array<out Annotation>?,
        retrofit: Retrofit?
    ): CallAdapter<*, *>? {
        if (returnType == null ||
            getRawType(returnType) != CoroutineLifecycleCall::class.java
        ) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("Call must be have generic type")
        }

        val responseType = getParameterUpperBound(0, returnType)
        return CoroutineCallEventHandleCallAdapter<Any>(responseType, enableCancel = enableCancel)
    }
}