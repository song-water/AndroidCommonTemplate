package com.water.song.template.common.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.water.song.template.common.R
import com.water.song.template.common.WLog

/**
 * @author Water-Song
 */
open class BaseActivity : AppCompatActivity() {
    @JvmField
    protected var logTag = ""

    @Suppress("MemberVisibilityCanBePrivate")
    protected val handler = Handler(Looper.getMainLooper())

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        logTag = javaClass.simpleName
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun pushFragment(fragment: Fragment?) {
        fragment ?: return
        supportFragmentManager.beginTransaction().run {
            replace(
                R.id.container,
                fragment,
                fragment.javaClass.simpleName + "_" + fragment.hashCode()
            )
            commit()
        }
        supportFragmentManager.executePendingTransactions()
    }

    protected fun popFragment() {
        try {
            supportFragmentManager.popBackStackImmediate()
        } catch (e: IllegalStateException) {
            WLog.e("", "popFragment: e:$e")
        } catch (e: NullPointerException) {
            WLog.e("", "popFragment: e:$e")
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BaseFragment) {
                if (it.onBackPressed()) {
                    return
                }
            }
        }
        super.onBackPressed()
    }
}