package com.lsl.wordhelper.util

import android.content.Context
import android.graphics.PixelFormat
import android.support.design.widget.TextInputLayout
import android.view.*
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.lsl.wh.R
import com.lsl.wordhelper.data.AppDatabase
import com.lsl.wordhelper.data.Word
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 *  http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1204/2103.html
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe: 创建WindowUtil对象，类似于 Java 中的静态工具类
 */
object WindowUtil {
    val TAG = "WindowUtil"
    lateinit var wm: WindowManager
    var isShow = false
    var mContent: String? = null

    var compositeDispose: CompositeDisposable? = null

    /**
     * 显示 window
     * @param context
     * @param content 粘贴板中的内容
     */
    fun showWindow(context: Context, content: String) {
        if (isShow) {
            return
        }
        isShow = true
        mContent = content
        wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val view = setUpView(context)
        val params = WindowManager.LayoutParams()
        with(params) {
            type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            // 此 flags 让 window 里面的控件可以获取焦点
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            format = PixelFormat.TRANSLUCENT
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
//            width = DisplayUtil.screenWidth(context.applicationContext) * (8 / 10)
//            height = DisplayUtil.screenWidth(context.applicationContext) * (3 / 10)
            gravity = Gravity.TOP
        }

        wm.addView(view, params)

        view.post {
            ViewAnimationUtils.createCircularReveal(view,
                    view.width / 2,
                    view.height / 2,
                    50f,
                    view.width.toFloat()).let {
                it.duration = 350
                it.interpolator = AccelerateInterpolator()
                it.start()
            }
        }
    }

    /**
     * 隐藏 window
     * @param view
     */
    private fun hideWindow(view: View?) {
        if (isShow && null != view) {
            loge(TAG, "hide window")
            wm.removeViewImmediate(view)
            isShow = false
        }
    }

    //    todo
    private fun setUpView(context: Context): View {

        val view = LayoutInflater.from(context).inflate(R.layout.window_layout2, null)
        with(view) {
            val tvOriginal = findViewById<TextView>(R.id.tv_window_original)
            val etTranslate = findViewById<EditText>(R.id.et_window_translate)
            val btnCancel = findViewById<Button>(R.id.btn_window_cancel)
            val btnOk = findViewById<Button>(R.id.btn_window_ok)
            mContent?.let { tvOriginal.text = it }
            btnCancel.setOnClickListener { WindowUtil.hideWindow(this) }
            btnOk.setOnClickListener {
                etTranslate.text.toString().let {
                    if (it.isNotBlank() && it.isNotEmpty()) {
                        val word = Word(originalContent = tvOriginal.text.toString(),
                                translatedContent = it)
                        insertWord(word, context)
                    }
                }
                WindowUtil.hideWindow(this)
            }

        }
//        val view = LayoutInflater.from(context)
//                .inflate(R.layout.window_layout, null)
//        with(view) {
//            val tvOriginal = findViewById<TextView>(R.id.tv_window_original)
//            val etTranslate = findViewById<EditText>(R.id.et_window_translate)
//            val btnCancel = findViewById<Button>(R.id.btn_window_cancel)
//            val btnOk = findViewById<Button>(R.id.btn_window_ok)
//            if (mContent != null) {
//                tvOriginal.text = mContent
//            }
//            btnCancel.setOnClickListener {
//                WindowUtil.hideWindow(this)
//            }
//            btnOk.setOnClickListener {
//                // 这里生成 Word 对象，然后传给 JobService 然后保存到数据库
//                context.toast(etTranslate.text.toString())
//                etTranslate.text.toString().run {
//                    if (this.isNotEmpty()) {
//                        val word = Word(originalContent = tvOriginal.text.toString(),
//                                translatedContent = this)
//                        insertWord(word, context)
//                    }
//                }
//                WindowUtil.hideWindow(this)
//            }
//        }
        return view
    }

    private fun insertWord(word: Word, context: Context) {
        if (compositeDispose == null) {
            compositeDispose = CompositeDisposable()
        }
        compositeDispose?.let {
            it.add(Observable.just(word)
                    .doOnSubscribe {
                        AppDatabase.getInstance(context).wordDao().insert(word)
                    }
                    .subscribeOn(Schedulers.io())
                    .subscribe())
        }
    }

    fun clear() {
        if (compositeDispose != null) {
            compositeDispose?.clear()
        }
    }
}
