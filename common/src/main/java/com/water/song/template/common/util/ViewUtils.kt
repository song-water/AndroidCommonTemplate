package com.water.song.template.common.util

import android.content.Context

/**
 * @author Water-Song
 */
fun Float.toDp(context: Context): Int {
    return this.dp2px(context)
}

fun Int.toDp(context: Context): Int {
    return this.toFloat().dp2px(context)
}

fun Float.dp2px(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.dp2px(context: Context): Int {
    val scale = context.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}