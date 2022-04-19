package com.water.song.template.app

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.water.song.template.home.SplashActivity
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * @author HelloWorld
 * LauncherActivityTest
 */
//@RunWith(HelloRobolectricTestRunner::class)
//@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
class LauncherActivityTest {

    private var context: Application? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        context = getApplicationContext()
    }

    @After
    fun tearDown() {
        context = null
    }

    @Test
    fun test_robolectric() {
        val bundle = Bundle()
        bundle.putString("Any", "HelloWorlLLLD")
        val uri = Uri.parse("http://www.baidu.com?word=himan")
        println("-->" + bundle.getString("Any"))
        println("-->" + uri.authority)
        println("-->" + uri.path)
        println("-->" + uri.port)
    }

    @Test
    @Ignore("Ignore for debug demo")
    fun test_onCreate_checkStartSplashActivity() {
        val startIntent = prepareLauncherIntent()

        ActivityScenario.launch<LauncherActivity>(startIntent).use { scenario ->
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.onActivity { activity ->
                assertTrue(activity.window.hasFeature(Window.FEATURE_NO_TITLE))

                assertEquals(
                    activity.componentName.className,
                    "com.water.song.template.app.LauncherActivity"
                )
                //期望的 intent
                val expected = Intent(activity, SplashActivity::class.java)
                //真实的 intent
                val actual: Intent = shadowOf(context).nextStartedActivity
                assertEquals(expected.component, actual.component)
            }
        }
    }

    private fun prepareLauncherIntent(): Intent {
        val startIntent = Intent(context, LauncherActivity::class.java)
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        startIntent.action = Intent.ACTION_MAIN
        startIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        return startIntent
    }

    @Test
    fun test_shouldFinishImmediately() {
        val startIntent = prepareLauncherIntent()
        ActivityScenario.launch<LauncherActivity>(startIntent).use { scenario ->
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.onActivity { activity ->
                val activityIntent = activity.intent

                assertTrue(activity.shouldFinishImmediately(false, activityIntent))
                assertFalse(activity.shouldFinishImmediately(true, activityIntent))

                activityIntent.removeCategory(Intent.CATEGORY_LAUNCHER)
                assertFalse(activity.shouldFinishImmediately(false, activityIntent))

                activityIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                activityIntent.action = null
                assertFalse(activity.shouldFinishImmediately(false, activityIntent))
            }
        }
    }

// 暂时无法模拟 isTaskRoot 为 false 的状态
//    @Test
//    fun test_onCreate_finishSelf() {
//        val startIntent = Intent(context, LauncherActivity::class.java)
//        startIntent.addCategory(Intent.CATEGORY_LAUNCHER)
//        startIntent.action = Intent.ACTION_MAIN
//        startIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
//
//        ActivityScenario.launch<LauncherActivity>(startIntent).use { scenario ->
//            scenario.moveToState(Lifecycle.State.CREATED)
//            scenario.onActivity { activity ->
//                println("intent: " + activity.isTaskRoot)
//
//                //期望的intent
//                val expected = Intent(activity, SplashActivity::class.java)
//
//                //真实的intent
//                val actual: Intent = shadowOf(context).nextStartedActivity
//                assertEquals(expected.component, actual.component)
//                activity.moveTaskToBack(true)
//            }
//        }
//
//
//        ActivityScenario.launch<LauncherActivity>(startIntent).use { scenario ->
//            scenario.moveToState(Lifecycle.State.CREATED)
//            scenario.onActivity { activity ->
//                println("2 intent: " + activity.isTaskRoot)
//
//                //期望的intent
//                val expected = Intent(activity, SplashActivity::class.java)
//
//                //真实的intent
//                val actual: Intent = shadowOf(context).nextStartedActivity
//                assertEquals(expected.component, actual.component)
//            }
//        }
//    }
}