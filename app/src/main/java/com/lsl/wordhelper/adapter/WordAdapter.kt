package com.lsl.wordhelper.adapter

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.lsl.wh.R
import com.lsl.wordhelper.data.Repository
import com.lsl.wordhelper.data.Word
import com.lsl.wordhelper.util.firstCharToUpperCase
import com.lsl.wordhelper.util.loge
import com.lsl.wordhelper.util.snackbar
import com.lsl.wordhelper.view.RoundBgTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by liusilong on 2018/1/16.
 * version:1.0
 * Describe:
 */
class WordAdapter(private var words: MutableList<Word>, private var type: Int = 0) : RecyclerView.Adapter<WordAdapter.WordViewHolder>()
        , RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {


    private val tag = "WordAdapter"
    private var compositeDisposable: CompositeDisposable? = null

    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WordViewHolder {
        context = parent?.context!!
        loge(tag, "onCreateViewHolder...")
        return WordViewHolder(parent, type)

    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: WordViewHolder?, position: Int) {
        holder?.bind(words[position])
        loge(tag, "onBindViewHolder。。。")
    }


    inner class WordViewHolder(parent: ViewGroup, type: Int) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_undo, parent, false)
    ) {
        lateinit var viewDoneBackground: RelativeLayout
        lateinit var viewUndoBackground: RelativeLayout
        lateinit var viewForeground: ConstraintLayout
        lateinit var tvOriginal: TextView
        lateinit var tvTranslate: TextView
        lateinit var tvDate: TextView
        lateinit var tvIcon: RoundBgTextView
//        var viewType: Int = 0

        init {
//            this.viewType = type
            with(itemView) {
                loge("adapter", "type: $type")
                viewDoneBackground = findViewById(R.id.background_item_done)
                viewUndoBackground = findViewById(R.id.background_item_undo)
                viewForeground = findViewById(R.id.foreground_item_undo)
                tvOriginal = findViewById(R.id.tv_item_original)
                tvTranslate = findViewById(R.id.tv_item_translate)
                tvDate = findViewById(R.id.tv_item_date)
                tvIcon = findViewById(R.id.tv_item_icon)
                if (type == 0) {
                    viewDoneBackground.visibility = View.GONE
                    viewUndoBackground.visibility = View.VISIBLE
                } else if (type == 1) {
                    viewDoneBackground.visibility = View.VISIBLE
                    viewUndoBackground.visibility = View.GONE
                }
            }
        }

        fun bind(word: Word) {
            tvOriginal.text = word.originalContent.firstCharToUpperCase()
            tvTranslate.text = word.translatedContent
            tvDate.text = word.date
            tvIcon.text = word.originalContent[0].toUpperCase().toString()
        }
    }


    fun update(newList: MutableList<Word>) {
        words = newList
        notifyDataSetChanged()

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        if (viewHolder is WordViewHolder) {
            removeItem(viewHolder.adapterPosition, viewHolder)
        }
    }

    /**
     * 删除
     */
    private fun removeItem(position: Int, viewHolder: RecyclerView.ViewHolder) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        val isComplete = words[position].isCompleted
        if (isComplete) {
            // 从数据库中删除
            compositeDisposable?.apply {
                add(Observable.just(position)
                        .doOnSubscribe {
                            loge(tag, "delete。。。")
                            Repository.providerWordRepository(context)
                                    .delete(words[position])
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
                )
            }

        } else {
//            将 word 的状态变更
            compositeDisposable?.apply {
                add(Observable.just(position)
                        .doOnSubscribe {
                            loge(tag, "doOnSubscribe...")
                            Repository.providerWordRepository(context)
                                    .updateById(words[position].id, true)
                        }
                        .doOnNext { loge(tag, "doOnNext...") }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val deletedWord = words[position]
                            words.removeAt(position)
                            notifyItemRemoved(position)
                            undo(deletedWord, position, viewHolder)
                        })
            }
        }
    }

    private fun undo(deletedWord: Word, deletedIndex: Int, viewHolder: RecyclerView.ViewHolder) {

        snackbar(view = viewHolder.itemView,
                msg = "${deletedWord.originalContent} 真的记住了吗???",
                actionTitle = "撤销",
                actionListener = View.OnClickListener {
                    restoreItem(deletedWord, deletedIndex)
                }
        )
    }

    /**
     * 恢复
     */
    private fun restoreItem(word: Word, position: Int) {
//        todo 需要将数据库中的状态变更
        if (!word.isCompleted) { //undo
            compositeDisposable?.add(
                    Observable.just(word)
                            .doOnSubscribe {
                                Repository.providerWordRepository(context)
                                        .updateById(word.id, false)
                            }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                word.isCompleted = false
                                words.add(position, word)
                                notifyItemInserted(position)
                            }
            )
        } else {

        }

    }

    fun clear() {
        compositeDisposable?.apply {
            clear()
        }
    }

}