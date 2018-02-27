package com.lsl.wordhelper.adapter

import android.support.v7.util.DiffUtil
import com.lsl.wordhelper.data.Word

/**
 * Created by liusilong on 2018/1/17.
 * version:1.0
 * Describe:
 */
class WordDiffCallback(private val oldList: MutableList<Word>, private val newList: MutableList<Word>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].originalContent == newList[newItemPosition].originalContent
    }

}