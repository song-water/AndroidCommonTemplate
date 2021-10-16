package com.water.song.template.network.demo

import com.water.song.template.network.CoroutineLifecycleCall
import okhttp3.ResponseBody
import retrofit2.http.GET

interface LibraryService {
    // 首页
    @GET("/")
    fun queryBaidu(): CoroutineLifecycleCall<ResponseBody>
}