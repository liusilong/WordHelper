package com.lsl.wordhelper.data

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcelable
import android.support.annotation.NonNull
import com.lsl.wordhelper.util.curDate
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

/**
 * Created by liusilong on 2018/1/13.
 * version:1.0
 * Describe:
 */

/**
 * 此数据类用于生产本地数据库（使用 Room）
 * @param id id 主键 id
 * @param originalContent 原始内容
 * @param translatedContent 翻译内容
 * @param isCompleted 是否完成
 *
 */
@Entity(tableName = "word")
@TypeConverters(DateConverter::class)
@Parcelize
@SuppressLint("ParcelCreator")
data class Word(
        @PrimaryKey(autoGenerate = true) // 主键 自增
        @ColumnInfo(name = "id")
        var id: Long = 0,

        //原文
        @ColumnInfo(name = "original_content")
        var originalContent: String = "",

        //释义
        @ColumnInfo(name = "translated_content")
        var translatedContent: String = "",

        //是否完成
        @ColumnInfo(name = "is_completed")
        var isCompleted: Boolean = false,

//        //日期
        @ColumnInfo(name = "date")
        var date: String = curDate()
) : Parcelable