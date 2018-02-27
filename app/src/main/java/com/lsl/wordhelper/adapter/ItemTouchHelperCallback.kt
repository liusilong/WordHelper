package com.lsl.wordhelper.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by liusilong on 2018/1/18.
 * version:1.0
 * Describe:
 */
class ItemTouchHelperCallback(private val itemTouchHelperImpl: ItemTouchHelperImpl) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        itemTouchHelperImpl.onItemDismiss(viewHolder?.adapterPosition!!)
    }

}