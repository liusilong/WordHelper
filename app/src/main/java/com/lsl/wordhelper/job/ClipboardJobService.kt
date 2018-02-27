package com.lsl.wordhelper.job

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.lsl.wordhelper.data.AppDatabase
import com.lsl.wordhelper.util.WindowUtil
import com.lsl.wordhelper.util.loge

/**
 * Created by liusilong on 2018/1/14.
 * version:1.0
 * Describe:监听粘贴板的 Service
 */

class ClipboardJobService : JobService() {
    private val TAG = "ClipboardJobService"
    private var clipboardManager: ClipboardManager? = null


    /**
     * 主线程中执行
     */
    override fun onStartJob(params: JobParameters?): Boolean {
        loge(TAG, "onStartJob is called")
        clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager?.let {
            initClipBoard()
        }
        return false
    }


    override fun onStopJob(params: JobParameters?): Boolean {
        loge(TAG, "onStopJob is called")
        return true
    }

    private fun initClipBoard() {
        with(clipboardManager) {
            this!!.addPrimaryClipChangedListener {
                val item: ClipData.Item? = primaryClip?.getItemAt(0)
                item?.text?.let {
                    loge(TAG, it.toString())
                    WindowUtil.showWindow(this@ClipboardJobService, it.toString())
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WindowUtil.clear()
    }

}