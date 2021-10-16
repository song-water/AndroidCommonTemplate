package com.water.song.template.network

import retrofit2.Response

interface CoroutineLifecycleCallback<T> {
    fun onSuccess(statusCode: Int, response: Response<T>)
    fun onFail(statusCode: Int, throwable: Throwable)
    fun onFinish()
}