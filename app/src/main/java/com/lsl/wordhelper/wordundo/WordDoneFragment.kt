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
 * Created by liusilong on 2018/1/20.
 * version:1.0
 * Describe: 已完成
 */
class WordDoneFragment : Fragment(), WordContract.View {

    override lateinit var presenter: WordContract.Presenter

    private val TAG = "WordDoneFragment"

    private lateinit var recyclerView: RecyclerView
    private val wordAdapter = WordAdapter(ArrayList(0), 1)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.frag_done_word, container, false)
        presenter = WordPresenter(Repository.providerWordRepository(context!!), this@WordDoneFragment)
        arguments
        with(root) {
            recyclerView = findViewById(R.id.recycler_view)
            recyclerView.let {
                it.layoutManager = LinearLayoutManager(activity?.applicationContext)
                it.addItemDecoration(DividerItemDecoration(context!!, DividerItemDecoration.VERTICAL))
                it.itemAnimator = DefaultItemAnimator()
                it.adapter = wordAdapter
                val itemTouchHelpCallback = RecyclerItemTouchHelper(
                        0,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                        wordAdapter
                )
                ItemTouchHelper(itemTouchHelpCallback).attachToRecyclerView(it)
            }
        }
        return root
    }

    override fun showAddWord() {

    }

    override fun showAllWord(words: MutableList<Word>) {
        wordAdapter.update(words)
    }

    override fun onResume() {
        super.onResume()
        loge(TAG, "onResume。。。")
        presenter.start(1)
    }

    companion object {
        fun newInstane() = WordDoneFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        wordAdapter.clear()
    }
}