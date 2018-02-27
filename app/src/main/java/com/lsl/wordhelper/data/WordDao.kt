package com.lsl.wordhelper.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

/**
 * Created by liusilong on 2018/1/16.
 * version:1.0
 * Describe:
 */
@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    /**
     * 查询未完成的 word
     */
    @Query("SELECT * FROM word WHERE is_completed = 0 ORDER BY id DESC")
    fun queryUnCompletedWords(): Flowable<MutableList<Word>>

    /**
     * 查询已完成的 word
     */
    @Query("SELECT * FROM word WHERE is_completed = 1 ORDER BY id DESC")
    fun queryCompletedWords(): Flowable<MutableList<Word>>

    /**
     * 根据 id 跟新 Word 状态
     */
    @Query("UPDATE word SET is_completed = :completed WHERE id = :wordId")
    fun updateById(wordId: Long, completed: Boolean)
}