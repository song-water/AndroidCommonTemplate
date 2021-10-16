package com.water.song.template.network

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertNull

@ExperimentalCoroutinesApi
class LifecycleCallExtensionKtTest {
    companion object {
        private const val TEST_MOCK_JSON = """
{
  "Mock": "Yes, this is mock"
}
    """
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val testScope = TestCoroutineScope()

    private val apiService: TestLibraryService = mockk()

    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        mockkStatic(CoroutineLifecycleCallExtensionKtPath)
    }

    @ObsoleteCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        testScope.cleanupTestCoroutines()
        unmockkAll()
    }

    @Test
    fun testAwaitTimeout_onFailureCalledSuccess() {
        val mockResultCallback = HttpResultCallback<ResponseBody>()
        coEvery {
            apiService.queryTestUrl().awaitTimeout(testScope, 10000)
        } returns mockResultCallback

        val exceptionMsg = "Timeout: no response within \$0"
        var httpResultError: HttpResult.Error? = null
        val mockHttpErrorResponse = HttpResult.Error(
            NetworkError(
                0, exceptionMsg, TimeoutException(exceptionMsg)
            )
        )
        apiService.queryTestUrl()
            .awaitTimeout(testScope, 10000)
            .onFailure {
                httpResultError = it
            }
            .onSuccess {}
            .onComplete {}
            .start()

        mockResultCallback.fail?.callback?.invoke(mockHttpErrorResponse)

        assertEquals(mockHttpErrorResponse, httpResultError)
    }

    @Test
    fun testAwaitTimeout_onSuccessCalledSuccess() {
        val mockResultCallback = HttpResultCallback<ResponseBody>()
        coEvery {
            apiService.queryTestUrl().awaitTimeout(testScope, 10000)
        } returns mockResultCallback

        var httpSuccessResponse: HttpResult.Okay<ResponseBody>? = null
        val mockHttpSuccessResponse = HttpResult.Okay(TEST_MOCK_JSON.toResponseBody(), mockk())
        apiService.queryTestUrl()
            .awaitTimeout(testScope, 10000)
            .onFailure {}
            .onSuccess {
                httpSuccessResponse = it
            }
            .onComplete {}
            .start()

        mockResultCallback.success?.callback?.invoke(mockHttpSuccessResponse)

        assertEquals(httpSuccessResponse, mockHttpSuccessResponse)
    }

    @Test
    fun testAwaitTimeout_onCompleteCalledSuccess() {
        val mockResultCallback = HttpResultCallback<ResponseBody>()
        coEvery {
            apiService.queryTestUrl().awaitTimeout(testScope, 10000)
        } returns mockResultCallback

        var completeResponse: Boolean? = null
        apiService.queryTestUrl()
            .awaitTimeout(testScope, 10000)
            .onFailure {}
            .onSuccess {}
            .onComplete {
                completeResponse = it
            }
            .start()

        mockResultCallback.complete?.callback?.invoke(true)

        assertEquals(true, completeResponse)
    }

    //TODO
    @Test
    fun testAwaitTimeout_onFailureCalledSuccessAfterSuspendAwaitFail() = runBlocking {
        val exceptionMsg = "Timeout: no response within \$0"
        val mockHttpErrorResponse = HttpResult.Error(
            NetworkError(
                0, exceptionMsg, TimeoutException(exceptionMsg)
            )
        )
        coEvery {
            apiService.queryTestUrl().suspendAwaitTimeout(10000)
        } returns mockHttpErrorResponse

        var httpResultError: HttpResult.Error? = null
        var httpSuccessResponse: HttpResult.Okay<ResponseBody>? = null
        var completeResponse: Boolean? = null
        apiService.queryTestUrl()
            .awaitTimeout(testScope, 10000)
            .onFailure {
                httpResultError = it
            }
            .onSuccess {
                httpSuccessResponse = it
            }
            .onComplete {
                completeResponse = it
            }
            .start()

        delay(500)

        assertNull(httpSuccessResponse)
        assertEquals(mockHttpErrorResponse, httpResultError)
        assertEquals(true, completeResponse)
    }

    @Test
    fun testAwaitTimeout_onSuccessCalledSuccessAfterSuspendAwaitOkay() = runBlocking {
        val mockHttpSuccessResponse = HttpResult.Okay(TEST_MOCK_JSON.toResponseBody(), mockk())
        coEvery {
            apiService.queryTestUrl().suspendAwaitTimeout(10000)
        } returns mockHttpSuccessResponse

        var httpResultError: HttpResult.Error? = null
        var httpSuccessResponse: HttpResult.Okay<ResponseBody>? = null
        var completeResponse: Boolean? = null
        apiService.queryTestUrl()
            .awaitTimeout(testScope, 10000)
            .onFailure {
                httpResultError = it
            }
            .onSuccess {
                httpSuccessResponse = it
            }
            .onComplete {
                completeResponse = it
            }
            .start()

        delay(500)

        assertNull(httpResultError)
        assertEquals(true, completeResponse)
        assertEquals(mockHttpSuccessResponse, httpSuccessResponse)
    }
}