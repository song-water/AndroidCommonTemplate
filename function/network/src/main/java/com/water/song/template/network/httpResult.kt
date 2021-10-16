@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.water.song.template.network

import okhttp3.Response

data class NetworkError(
    val errorCode: Int,
    val errorMessage: String?,
    val exception: Exception
)

sealed class HttpResult<out T : Any> {
    class Okay<out T : Any>(
        val value: T,
        val response: Response
    ) : HttpResult<T>() {
        override fun toString(): String {
            return "HttpResult.Okay{value=$value, response=$response}"
        }
    }

    class Error(
        val error: NetworkError
    ) : HttpResult<Nothing>() {
        override fun toString(): String {
            return "HttpResult.Error{error=$error}"
        }
    }
}

inline fun <T : Any> HttpResult<T>.onFailure(
    action: (HttpResult.Error) -> Any
): HttpResult<T> {
    return if (this is HttpResult.Error) {
        action(this)
        this
    } else {
        this
    }
}

inline fun <A : Any, V : Any> HttpResult<A>.onSuccess(
    action: (HttpResult.Okay<A>) -> HttpResult<V>?
): HttpResult<V>? {
    return when (this) {
        is HttpResult.Okay -> {
            action(this)
        }
        is HttpResult.Error -> {
            this
        }
    }
}

inline fun <T : Any, R : Any> HttpResult<T>?.followDo(
    action: (HttpResult<T>?) -> R
): R {
    return action(this)
}
