package com.water.song.template.common

/**
 * @author Water-Song
 */
class WLog {
    companion object {
        @JvmStatic
        fun i(tag: String, message: String): Int {
            return android.util.Log.i(tag, message)
        }

        @JvmStatic
        fun w(tag: String, message: String): Int {
            return android.util.Log.w(tag, message)
        }

        @JvmStatic
        fun w(tag: String, throwable: Throwable): Int {
            return android.util.Log.w(tag, throwable)
        }

        @JvmStatic
        fun e(tag: String, message: String): Int {
            return android.util.Log.e(tag, message)
        }

        @JvmStatic
        fun e(tag: String, throwable: Throwable): Int {
            return android.util.Log.e(tag, throwable.message, throwable)
        }
    }
}