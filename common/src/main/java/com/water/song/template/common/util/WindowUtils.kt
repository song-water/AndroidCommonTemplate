package com.water.song.template.common.util

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

/**
 * @author Water-Song
 */
fun Window?.makeImmersive() {
    this ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ViewCompat.getWindowInsetsController(decorView)?.let { compat ->
            compat.show(WindowInsetsCompat.Type.statusBars())
            compat.show(WindowInsetsCompat.Type.navigationBars())
            compat.systemBarsBehavior = BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        statusBarColor = Color.TRANSPARENT
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        statusBarColor = Color.TRANSPARENT
    }
}

fun Window?.adjustImeLayoutWithView(view: View) {
    this ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        return
    }
    view.setOnFocusChangeListener { v, hasFocus ->
        if (hasFocus) {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}

fun Window?.applyWindowInsets(rootView: View) {
    this ?: return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        return
    }
    setDecorFitsSystemWindows(false)
    ViewCompat.setOnApplyWindowInsetsListener(rootView, OnApplyWindowInsetsListener { view, insets ->
        insets.toWindowInsets()?.let {
            val imeHeight = it.getInsets(WindowInsets.Type.ime()).bottom
            val statusBarHeight = it.getInsets(WindowInsets.Type.statusBars()).top
            val naviBarHeight = it.getInsets(WindowInsets.Type.navigationBars()).bottom
            if (it.isVisible(WindowInsets.Type.ime())) {
                view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, imeHeight)
            } else {
                view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, naviBarHeight)
            }
        }
        insets.consumed()
    })
}