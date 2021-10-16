package com.water.song.template.network.demo

import com.water.song.template.network.CoroutineLifecycleCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class LibraryCallImpl {

    private val libraryCallUrl = "https://www.baidu.com"

    private val okHttpClient by lazy {
        OkHttpClient
            .Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .proxy(Proxy.NO_PROXY)
            .addInterceptor(HttpLoggingInterceptor().also {
                HttpLoggingInterceptor.Level.BODY
//                it.level = if (BuildConfig.DEBUG) {
//                    HttpLoggingInterceptor.Level.BODY
//                } else {
//                    HttpLoggingInterceptor.Level.BASIC
//                }
            })
            .build()
    }

    fun <T> callTestApi(clazz: Class<T>): T {
        return callLibraryTestRetrofit().create(clazz)
    }

    private fun callLibraryTestRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .client(
                okHttpClient.newBuilder().addInterceptor(InterceptorModifyRequest()).build()
            )
            .baseUrl(libraryCallUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineLifecycleCallAdapterFactory())
            .build()
    }

    companion object {
        @JvmStatic
        fun getInstance(): LibraryCallImpl = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE = LibraryCallImpl()
    }
}