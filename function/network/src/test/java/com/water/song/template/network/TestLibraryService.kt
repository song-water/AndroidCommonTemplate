package com.water.song.template.network

import okhttp3.ResponseBody
import retrofit2.http.GET

internal interface TestLibraryService {
    //首页
    @GET("/")
    fun queryTestUrl(): CoroutineLifecycleCall<ResponseBody>
}