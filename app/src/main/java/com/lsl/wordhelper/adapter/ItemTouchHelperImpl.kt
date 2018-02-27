package com.lsl.wordhelper.adapter

/**
 * Created by liusilong on 2018/1/18.
 * version:1.0
 * Describe:
 */
interface ItemTouchHelperImpl {
    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)
}