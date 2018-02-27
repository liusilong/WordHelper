package com.lsl.wordhelper.util

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast

/**
 * Created by liusilong on 2018/1/14.
 * version:1.0
 * Describe: Context 扩展方法
 */
fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}


fun snackbar(view: View, msg: String,
             duration: Int = Snackbar.LENGTH_SHORT,
             actionTitle: String? = null,
             actionListener: View.OnClickListener? = null) {
    Snackbar.make(view, msg, duration).setAction(actionTitle, actionListener).show()
}