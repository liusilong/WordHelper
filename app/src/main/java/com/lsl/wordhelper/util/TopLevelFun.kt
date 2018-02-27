package com.lsl.wordhelper.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by liusilong on 2018/1/14.
 * version:1.0
 * Describe:
 */

fun loge(tag: String, msg: String) {
    Log.e(tag, msg)
}

@SuppressLint("SimpleDateFormat")
fun curDate(): String {
    val format = SimpleDateFormat("yyyy/MM/dd")
    val date = Date()
    return format.format(date)
}