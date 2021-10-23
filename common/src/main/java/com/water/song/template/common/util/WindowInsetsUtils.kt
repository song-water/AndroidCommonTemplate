package com.water.song.template.common.util

import android.os.Build
import android.view.WindowInsets
import androidx.core.view.WindowInsetsCompat

/**
 * @author Water-Song
 */
fun WindowInsetsCompat.consumed(): WindowInsetsCompat {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        WindowInsetsCompat.toWindowInsetsCompat(WindowInsets.CONSUMED)
    } else {
        consumeSystemWindowInsets()
    }
}