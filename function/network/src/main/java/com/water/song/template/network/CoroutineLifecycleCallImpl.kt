package com.water.song.template.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoroutineLifecycleCallImpl<T>(
    private val callImpl: Call<T>,
    private val enableCancel: Boolean
) : CoroutineLifecycleCall<T> {

    override fun cancel() {
        callImpl.cancel()
    }

    override fun enqueue(callback: CoroutineLifecycleCallback<T>) {
        callImpl.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onFail(0, t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                callback.onSuccess(response.code(), response)
            }
        })
    }

    override fun clone(): CoroutineLifecycleCall<T> {
        return CoroutineLifecycleCallImpl<T>(callImpl.clone(), enableCancel)
    }

    override fun isCanceled(): Boolean {
        return callImpl.isCanceled
    }

    override fun enableCancel(): Boolean {
        return enableCancel
    }

}