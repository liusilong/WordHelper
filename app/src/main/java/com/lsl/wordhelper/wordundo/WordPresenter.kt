package com.lsl.wordhelper.wordundo

import com.lsl.wordhelper.data.Word
import com.lsl.wordhelper.data.WordDao
import com.lsl.wordhelper.util.loge
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe:
 *
 */
class WordPresenter(val wordDao: WordDao,
                    val wordView: WordContract.View) : WordContract.Presenter {
    private val tag = "WordPresenter"
    private val compositeDisposable = CompositeDisposable()

    override fun start(type: Int) {
        if (type == 0) {
            loadUndoData()
        } else if (type == 1) {
            loadCompleteData()
        }

    }


    /**
     * 从数据库加载数据
     */
    private fun loadUndoData() {
        loge(tag, "loadUndoData。。。")
        compositeDisposable.add(wordDao.queryUnCompletedWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    it?.let {
                        wordView.showAllWord(it)
                    }
                }
                .subscribe())
    }

    private fun loadCompleteData() {
        compositeDisposable.add(wordDao.queryCompletedWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    it?.let {
                        wordView.showAllWord(it)
                    }
                }
                .subscribe()
        )
    }

    /**
     * 添加添加按钮时候出发
     */
    override fun addNewWord() {
        wordView.showAddWord()
    }

    /**
     * 将新词汇添加到数据库
     */
    override fun insertNewWord(word: Word) {
        compositeDisposable.add(Observable.just(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }


}