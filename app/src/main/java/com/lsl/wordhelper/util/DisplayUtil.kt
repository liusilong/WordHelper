package com.lsl.wordhelper.util

import android.content.Context

/**
 * Created by liusilong on 2018/1/17.
 * version:1.0
 * Describe:
 */
object DisplayUtil {
    fun screenWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    fun screenHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }
}