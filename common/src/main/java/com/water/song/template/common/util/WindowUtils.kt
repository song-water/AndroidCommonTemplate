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
import androidx.core.view.WindowInsetsControllerCompat

/**
 * @author Water-Song
 */
fun Window?.makeImmersive() {
    this ?: return
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
            ViewCompat.getWindowInsetsController(decorView)?.also { compat ->
                compat.show(WindowInsetsCompat.Type.statusBars())
                compat.show(WindowInsetsCompat.Type.navigationBars())
                compat.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val currentVisibility = decorView.systemUiVisibility
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or currentVisibility
            statusBarColor = Color.TRANSPARENT
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            val currentVisibility = decorView.systemUiVisibility
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or currentVisibility
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun Window?.adjustImeLayoutWithView(view: View?) {
    this ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        return
    }
    view?.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}

fun Window?.applyWindowInsets(attachView: View?) {
    this ?: return
    attachView ?: return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        return
    }
    setDecorFitsSystemWindows(false)
    ViewCompat.setOnApplyWindowInsetsListener(attachView) { view, insetsCompat ->
        insetsCompat.toWindowInsets()?.let { insets ->
            val imeHeight = insets.getInsets(WindowInsets.Type.ime()).bottom
            val statusBarHeight = insets.getInsets(WindowInsets.Type.statusBars()).top
            if (insets.isVisible(WindowInsets.Type.ime())) {
                view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, imeHeight)
            } else {
                val navHeight = insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                view.setPadding(view.paddingLeft, statusBarHeight, view.paddingRight, navHeight)
            }
        }
        insetsCompat.consumed()
    }
}