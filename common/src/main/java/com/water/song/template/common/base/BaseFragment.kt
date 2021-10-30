package com.water.song.template.common.base

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.water.song.template.common.WsBaseApplication

/**
 * @author Water-Song
 */
open class BaseFragment : Fragment() {
    @JvmField
    protected var logTag = ""

    @Suppress("MemberVisibilityCanBePrivate")
    protected val handler = Handler(Looper.getMainLooper())
    protected val safeContext = WsBaseApplication.instance
    protected val safeActivity: Activity?
        get() {
            val ret = activity
            return if (ret != null && !ret.isFinishing && !ret.isDestroyed) {
                ret
            } else {
                null
            }
        }
    val activityAvailable: Boolean
        get() {
            return !(activity == null || requireActivity().isFinishing || requireActivity().isDestroyed)
        }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logTag = javaClass.simpleName
    }

    open fun onBackPressed(): Boolean {
        return false
    }

    @CallSuper
    override fun onDetach() {
        super.onDetach()
        handler.removeCallbacksAndMessages(null)
    }

    protected fun finishActivity() {
        activity?.finish()
    }
}