package com.water.song.template.unittests

import android.app.Application
import org.robolectric.DefaultTestLifecycle
import org.robolectric.RobolectricTestRunner
import org.robolectric.TestLifecycle
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowLog
import java.lang.reflect.Method

/**
 * @author HelloWorld
 * DemoRobolectricTestRunner
 */
class HelloRobolectricTestRunner(testClass: Class<*>?) : RobolectricTestRunner(testClass) {

    companion object {
        private const val DEFAULT_SDK = 31
    }

    @Deprecated("Deprecated in Java")
    override fun buildGlobalConfig(): Config {
        return Config.Builder()
            .setApplication(Application::class.java)
            .setSdk(DEFAULT_SDK)
            .setManifest(Config.NONE)
            .build()
    }

    override fun getTestLifecycleClass(): Class<out TestLifecycle<Any>> {
        return BaseTestLifecycle::class.java
    }

    class BaseTestLifecycle: DefaultTestLifecycle() {
        override fun beforeTest(method: Method?) {
            ShadowLog.stream = System.out
            super.beforeTest(method)
        }
    }
}