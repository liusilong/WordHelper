package com.lsl.wordhelper

/**
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe:
 */
interface BasePresenter {
    /**
     * type: 0 --> undo
     * type: 1 --> done
     */
    fun start(type: Int)
}