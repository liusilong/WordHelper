package com.lsl.wordhelper.wordundo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lsl.wh.R

/**
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe: 显示单侧列表
 * 此 Activity 启动的时候回启动一个 JobService
 */
class WordListActivity : AppCompatActivity() {
    private lateinit var wordPresent: WordPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordlist)
    }

    override fun onResume() {
        super.onResume()
    }
}