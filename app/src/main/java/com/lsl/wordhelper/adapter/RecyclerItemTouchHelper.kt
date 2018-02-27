package com.lsl.wordhelper.adapter

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by liusilong on 2018/1/19.
 * version:1.0
 * Describe:
 */
class RecyclerItemTouchHelper(dragDirs: Int, swipeDirs: Int, val listener: RecyclerItemTouchHelperListener)
    : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        viewHolder?.let {
            listener.onSwiped(it, direction, it.adapterPosition)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            val foregroundView = (it as WordAdapter.WordViewHolder).viewForeground
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    override fun onChildDrawOver(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        viewHolder?.let {
            val foregroundView = (it as WordAdapter.WordViewHolder).viewForeground
            ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(
                    c,
                    recyclerView,
                    foregroundView,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
            )
        }
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        viewHolder?.let {
            val foregroundView = (it as WordAdapter.WordViewHolder).viewForeground
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foregroundView)
        }
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        viewHolder?.let {
            val foregroundView = (it as WordAdapter.WordViewHolder).viewForeground
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(
                    c,
                    recyclerView,
                    foregroundView,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
            )
        }
    }

    interface RecyclerItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}