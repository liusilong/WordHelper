package com.lsl.wordhelper.wordundo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lsl.wh.R
import com.lsl.wordhelper.adapter.RecyclerItemTouchHelper
import com.lsl.wordhelper.adapter.WordAdapter
import com.lsl.wordhelper.data.Repository
import com.lsl.wordhelper.data.Word
import com.lsl.wordhelper.util.loge

/**
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe: 显示单词列表
 */
class WordUndoFragment : Fragment(), WordContract.View {


    private val TAG = "WordUndoFragment"
    private lateinit var recyclerView: RecyclerView
    override lateinit var presenter: WordContract.Presenter

    private val wordAdapter = WordAdapter(ArrayList(0), 0)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.frag_undo_word, container, false)
        presenter = WordPresenter(Repository.providerWordRepository(context!!), this)
        with(root) {
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.let {
                it.layoutManager = LinearLayoutManager(activity?.applicationContext)
                it.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
                it.itemAnimator = DefaultItemAnimator()
                it.setHasFixedSize(true)
                it.adapter = wordAdapter


                val itemTouchHelpCallback = RecyclerItemTouchHelper(
                        0,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                        wordAdapter
                )
                ItemTouchHelper(itemTouchHelpCallback).attachToRecyclerView(it)
//                val callback = ItemTouchHelperCallback(wordAdapter)
//                val touchHelper = ItemTouchHelper(callback)
//                touchHelper.attachToRecyclerView(it)
            }
        }
        return root
    }


    /**
     * -----------------WordContract.View start---------------
     */

    override fun showAllWord(words: MutableList<Word>) {
        loge(TAG, "showAllWord...")
        words.map { it.toString() }.forEach { loge("showAllWord:", it) }
        wordAdapter.update(words)

    }

    /**
     * 显示添加新词汇的 Dialog
     * 添加时调用 WordPresenter#insertNewWord 存到数据库
     */
    override fun showAddWord() {
    }

    override fun onResume() {
        super.onResume()
        presenter.start(0)
    }

    /**
     * 伴生对象
     * 类似于 Java 中的 public static Fragment newInstance(){...}
     */
    companion object {
        fun newInstance() = WordUndoFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        wordAdapter.clear()
    }

}