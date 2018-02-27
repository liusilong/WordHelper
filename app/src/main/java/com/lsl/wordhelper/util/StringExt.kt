package com.lsl.wordhelper.util

/**
 * Created by liusilong on 2018/1/19.
 * version:1.0
 * Describe:
 */
/**
 * 首字母大写
 */
fun String.firstCharToUpperCase(): String {
    return this[0].toUpperCase() + this.substring(1)
}