package com.lsl.wordhelper.wordundo

import com.lsl.wordhelper.BasePresenter
import com.lsl.wordhelper.BaseView
import com.lsl.wordhelper.data.Word

/**
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe:
 */
interface WordContract {

    /**
     * WordListFragment 实现
     *
     *
     */
    interface View : BaseView<Presenter> {

        //添加新词汇时候用于显示界面
        fun showAddWord()

        //用于显示所有的词汇
        fun showAllWord(words: MutableList<Word>)

    }

    /**
     * todo
     * 用于 WordPresenter 实现
     * 里面需要实现的的哪些功能的接口
     */
    interface Presenter : BasePresenter {

        //添加新词汇
        fun addNewWord()

        //将新词汇添加到数据库
        fun insertNewWord(word: Word)


    }

}